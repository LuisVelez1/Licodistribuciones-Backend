package com.backendintranet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;;

@Entity
@Table(name = "vw_birthdays")
@Getter
@Setter
@Immutable
public class BirthdayUser {
    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    private String sede;
    private String status;
}