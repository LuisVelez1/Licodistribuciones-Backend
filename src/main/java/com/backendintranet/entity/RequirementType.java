package com.backendintranet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "requirement_types")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RequirementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;
}