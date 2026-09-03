package com.backendintranet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AdminUpdateUserRequest {
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email(message = "Formato de email inválido")
    private String email;

    @Size(min = 6, message = "Mínimo 6 caracteres")
    private String password;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String cedula;

    @Size(max = 100)
    private String position;

    @Size(max = 100)
    private String sede;

    private Integer areaId;

    private String status;

    private LocalDate birthDate;
}