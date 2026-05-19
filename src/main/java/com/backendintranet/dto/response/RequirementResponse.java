package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequirementResponse {

    private Integer id;
    private String title;
    private String description;

    private Integer areaId;
    private String areaName;

    private Integer typeId;
    private String typeName;

    private String status;
    private String priority;

    private String createdById;
    private String createdByName;

    private String assignedToId;
    private String assignedToName;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Boolean active;
}