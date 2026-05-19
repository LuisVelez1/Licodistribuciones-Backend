package com.backendintranet.controller;

import com.backendintranet.dto.request.DocumentUpdateRequest;
import com.backendintranet.dto.request.DocumentUploadRequest;
import com.backendintranet.dto.response.DocumentResponse;
import com.backendintranet.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestPart("data") @Valid DocumentUploadRequest request,
            @RequestPart("file") MultipartFile file,
            @RequestParam String userId
    ) {

        return ResponseEntity.ok(
                documentService.uploadDocument(request, file, userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAll() {

        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getById(@PathVariable String id) {

        return ResponseEntity.ok(
                documentService.getDocumentById(id)
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<DocumentResponse>> getByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(
                documentService.getDocumentsByCategory(category)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponse> update(
            @PathVariable String id,
            @RequestBody @Valid DocumentUpdateRequest request,
            @RequestParam String userId
    ) {

        return ResponseEntity.ok(
                documentService.updateDocument(id, request, userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {

        DocumentResponse doc = documentService.getDocumentById(id);

        try {

            Path path = Paths.get(doc.getFileUrl());

            byte[] fileBytes = Files.readAllBytes(path);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + doc.getOriginalFileName() + "\""
                    )
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error al descargar archivo");
        }
    }
}