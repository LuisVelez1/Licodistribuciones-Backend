package com.backendintranet.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequirementUpdateRequest {
    private String title;
    private String description;
    private Integer areaId;
    private Integer typeId;
    private String priority;
    private String status;
    private String assignedTo;
    private LocalDateTime dueDate;
}