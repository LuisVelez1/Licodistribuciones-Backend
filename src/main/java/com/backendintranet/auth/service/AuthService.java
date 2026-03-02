package com.backendintranet.auth.service;

import com.backendintranet.auth.dto.LoginRequest;
import com.backendintranet.auth.dto.LoginResponse;
import com.backendintranet.auth.entity.User;
import com.backendintranet.auth.repository.UserRepository;
import com.backendintranet.auth.util.Sha256Util;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null || request.email() == null || request.password() == null
                || request.email().isBlank() || request.password().isBlank()) {
            return new LoginResponse(false, "Email y contraseña son obligatorios");
        }

        Optional<User> user = userRepository.findByEmail(request.email().trim().toLowerCase());

        if (user.isEmpty()) {
            return new LoginResponse(false, "Credenciales inválidas");
        }

        String requestHash = Sha256Util.hash(request.password());
        boolean authenticated = requestHash.equals(user.get().getPasswordSha256());

        if (!authenticated) {
            return new LoginResponse(false, "Credenciales inválidas");
        }

        return new LoginResponse(true, "Inicio de sesión exitoso");
    }
}
