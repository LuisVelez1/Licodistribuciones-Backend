package com.backendintranet.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedAssetResponse {

    private String id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String model;
    private String serial;
    private String location;
    private String sede;
    private Integer areaId;
    private String areaName;
    private String assignedToId;
    private String assignedToFullName;
    private String status;
    private LocalDateTime acquisitionDate;
    private BigDecimal acquisitionValue;
    private String description;
    private String processor;
    private String ram;
    private String storage;
    private String os;
    private String ip;
    private String mac;

    private LocalDateTime warrantyDate;
    private Boolean actaFirmada;
    private LocalDateTime actaDate;
}