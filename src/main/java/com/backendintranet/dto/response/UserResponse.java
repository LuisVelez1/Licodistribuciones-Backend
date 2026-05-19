package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private String sede;
    private String status;
    private Integer areaId;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Set<String> roles;
}
