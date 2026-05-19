package com.backendintranet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fixed_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedAsset {

    @Id
    @Column(length = 36)
    private String id;

    @Column(length = 50)
    private String code;

    @Column(length = 150)
    private String name;

    @Column(length = 100)
    private String category;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String model;

    @Column(length = 100)
    private String serial;

    @Column(length = 150)
    private String location;

    @Column(length = 100)
    private String sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(length = 30)
    private String status;

    @Column(name = "acquisition_date")
    private LocalDateTime acquisitionDate;

    @Column(name = "acquisition_value", precision = 10, scale = 2)
    private BigDecimal acquisitionValue;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String processor;

    @Column(length = 50)
    private String ram;

    @Column(length = 50)
    private String storage;

    @Column(length = 100)
    private String os;

    @Column(length = 50)
    private String ip;

    @Column(length = 50)
    private String mac;

    @Column(name = "warranty_date")
    private LocalDateTime warrantyDate;

    @Column(name = "acta_firmada")
    private Boolean actaFirmada = false;

    @Column(name = "acta_date")
    private LocalDateTime actaDate;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }
}