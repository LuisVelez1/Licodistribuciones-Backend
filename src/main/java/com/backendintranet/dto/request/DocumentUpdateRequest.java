package com.backendintranet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentUpdateRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200)
    private String title;

    private String description;

    @Size(max = 100)
    private String category;

    private Integer areaId;

    @Size(max = 20)
    private String version;

    private Boolean isPublic;

    private Boolean isActive;
}