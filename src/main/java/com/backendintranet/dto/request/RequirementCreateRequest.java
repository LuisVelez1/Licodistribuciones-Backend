package com.backendintranet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequirementCreateRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String description;

    @NotNull(message = "El área es obligatoria")
    private Integer areaId;

    @NotNull(message = "El tipo es obligatorio")
    private Integer typeId;

    @NotBlank(message = "La prioridad es obligatoria")
    private String priority;

    private LocalDateTime dueDate;
}