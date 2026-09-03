package com.backendintranet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequirementCommentRequest {

    @NotBlank(message = "El comentario es obligatorio")
    private String comment;
}