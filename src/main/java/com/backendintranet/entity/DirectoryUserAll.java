package com.backendintranet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;


@Entity
@Table(name = "vw_directory_users_all")
@Getter
@Setter
@Immutable
public class DirectoryUserAll {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String phone;
    private String position;
    private String sede;
    private String status;

    @Column(name = "area_name")
    private String areaName;
}