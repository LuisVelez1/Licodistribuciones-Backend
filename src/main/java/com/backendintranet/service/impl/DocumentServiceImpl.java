package com.backendintranet.service.impl;

import com.backendintranet.dto.request.DocumentUpdateRequest;
import com.backendintranet.dto.request.DocumentUploadRequest;
import com.backendintranet.dto.response.DocumentResponse;
import com.backendintranet.entity.Area;
import com.backendintranet.entity.Document;
import com.backendintranet.entity.User;
import com.backendintranet.repository.AreaRepository;
import com.backendintranet.repository.DocumentRepository;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;

    private final String UPLOAD_DIR = "uploads/documents";

    @Override
    public DocumentResponse uploadDocument(
            DocumentUploadRequest request,
            MultipartFile file,
            String userId
    ) {

        try {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Area area = null;

            if (request.getAreaId() != null) {
                area = areaRepository.findById(request.getAreaId())
                        .orElseThrow(() -> new RuntimeException("Área no encontrada"));
            }

            String originalFileName = file.getOriginalFilename();

            String extension = getFileExtension(originalFileName);

            String storedFileName =
                    UUID.randomUUID() + "." + extension;

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Document document = Document.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .category(request.getCategory())
                    .documentType(extension.toUpperCase())
                    .version(
                            request.getVersion() != null
                                    ? request.getVersion()
                                    : "1.0"
                    )
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileExtension(extension)
                    .mimeType(file.getContentType())
                    .fileSize(file.getSize())
                    .fileUrl(filePath.toString())
                    .isPublic(
                            request.getIsPublic() != null
                                    ? request.getIsPublic()
                                    : false
                    )
                    .isActive(true)
                    .area(area)
                    .uploadedBy(user)
                    .updatedBy(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Document saved = documentRepository.save(document);

            return mapToResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir documento");
        }
    }

    @Override
    public List<DocumentResponse> getAllDocuments() {

        return documentRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DocumentResponse getDocumentById(String id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        return mapToResponse(document);
    }

    @Override
    public List<DocumentResponse> getDocumentsByCategory(String category) {

        return documentRepository
                .findByCategoryAndIsActiveTrue(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DocumentResponse updateDocument(
            String id,
            DocumentUpdateRequest request,
            String userId
    ) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Area area = null;
        if (request.getAreaId() != null) {
            area = areaRepository.findById(request.getAreaId())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        }

        document.setTitle(request.getTitle());
        document.setDescription(request.getDescription());
        document.setCategory(request.getCategory());
        document.setVersion(request.getVersion());
        document.setIsPublic(request.getIsPublic());
        document.setIsActive(request.getIsActive());
        document.setArea(area);
        document.setUpdatedBy(user);

        documentRepository.save(document);

        Document updated = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        return mapToResponse(updated);
    }

    @Override
    public void deleteDocument(String id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        document.setIsActive(false);

        documentRepository.save(document);
    }

    private String getFileExtension(String fileName) {

        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private DocumentResponse mapToResponse(Document document) {

        return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .description(document.getDescription())
                .category(document.getCategory())
                .documentType(document.getDocumentType())
                .version(document.getVersion())
                .originalFileName(document.getOriginalFileName())
                .fileExtension(document.getFileExtension())
                .fileSize(document.getFileSize())
                .fileUrl(document.getFileUrl())
                .isPublic(document.getIsPublic())
                .isActive(document.getIsActive())
                .areaName(
                        document.getArea() != null
                                ? document.getArea().getName()
                                : null
                )
                .uploadedBy(
                        document.getUploadedBy() != null
                                ? document.getUploadedBy().getFirstName()
                                + " "
                                + document.getUploadedBy().getLastName()
                                : null
                )
                .uploadedById(
                        document.getUploadedBy() != null
                                ? document.getUploadedBy().getId()
                                : null
                )
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}