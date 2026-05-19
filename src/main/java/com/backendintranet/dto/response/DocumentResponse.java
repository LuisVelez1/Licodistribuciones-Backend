package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentResponse {

    private String id;

    private String title;

    private String description;

    private String category;

    private String documentType;

    private String version;

    private String originalFileName;

    private String fileExtension;

    private Long fileSize;

    private String fileUrl;

    private Boolean isPublic;

    private Boolean isActive;

    private String areaName;

    private String uploadedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String uploadedById;
}