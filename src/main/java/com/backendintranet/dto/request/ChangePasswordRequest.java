package com.backendintranet.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Size(min = 6)
    private String newPassword;
}