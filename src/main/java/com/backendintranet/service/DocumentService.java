package com.backendintranet.service;

import com.backendintranet.dto.request.DocumentUpdateRequest;
import com.backendintranet.dto.request.DocumentUploadRequest;
import com.backendintranet.dto.response.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponse uploadDocument(
            DocumentUploadRequest request,
            MultipartFile file,
            String userId
    );

    List<DocumentResponse> getAllDocuments();

    DocumentResponse getDocumentById(String id);

    List<DocumentResponse> getDocumentsByCategory(String category);

    DocumentResponse updateDocument(
            String id,
            DocumentUpdateRequest request,
            String userId
    );

    void deleteDocument(String id);

}