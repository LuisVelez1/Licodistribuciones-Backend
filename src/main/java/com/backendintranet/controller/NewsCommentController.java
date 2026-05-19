package com.backendintranet.controller;

import com.backendintranet.dto.request.NewsCommentRequest;
import com.backendintranet.dto.response.NewsCommentResponse;
import com.backendintranet.service.NewsCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsCommentController {

    private final NewsCommentService commentService;

    @PostMapping("/{newsId}/comments")
    public NewsCommentResponse createComment(
            @PathVariable Integer newsId,
            @Valid @RequestBody NewsCommentRequest request
    ) {

        return commentService.createComment(newsId, request);
    }

    @GetMapping("/{newsId}/comments")
    public List<NewsCommentResponse> getComments(
            @PathVariable Integer newsId
    ) {

        return commentService.getCommentsByNews(newsId);
    }
}