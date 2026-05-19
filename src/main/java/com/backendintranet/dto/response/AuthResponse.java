package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String user;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
