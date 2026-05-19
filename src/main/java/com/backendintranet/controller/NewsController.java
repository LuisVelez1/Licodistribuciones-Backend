package com.backendintranet.controller;

import com.backendintranet.dto.request.NewsRequest;
import com.backendintranet.dto.response.NewsResponse;
import com.backendintranet.entity.User;
import com.backendintranet.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponse>> getAll() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsResponse> create(
            @RequestPart("data") NewsRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                newsService.create(request, file, user)
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsResponse> update(
            @PathVariable Integer id,
            @RequestPart("data") NewsRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                newsService.update(id, request, file, user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        newsService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}