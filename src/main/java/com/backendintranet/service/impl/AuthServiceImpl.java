package com.backendintranet.service.impl;
import com.backendintranet.dto.request.LoginRequest;
import com.backendintranet.dto.response.AuthResponse;
import com.backendintranet.entity.Role;
import com.backendintranet.entity.User;
import com.backendintranet.exception.BadRequestException;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.AuthService;
import com.backendintranet.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {

        String usernameInput = request.getUsuario().toUpperCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameInput, request.getPassword())
        );

        User user = userRepository.findByUsername(usernameInput)
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        String token = jwtService.generateToken(user);
        return buildAuthResponse(user, token);
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return AuthResponse.builder()
                .token(token)
                .user(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(roles)
                .build();
    }
}
