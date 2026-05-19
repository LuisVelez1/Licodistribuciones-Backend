package com.backendintranet.service;


import com.backendintranet.dto.request.LoginRequest;
import com.backendintranet.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
