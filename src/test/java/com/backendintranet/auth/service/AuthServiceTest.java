package com.backendintranet.auth.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.backendintranet.auth.dto.LoginRequest;
import com.backendintranet.auth.dto.LoginResponse;
import com.backendintranet.auth.entity.User;
import com.backendintranet.auth.repository.UserRepository;
import com.backendintranet.auth.util.Sha256Util;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldAuthenticateWhenEmailAndPasswordMatch() {
        User user = new User();
        user.setEmail("admin@licodistribuciones.com");
        user.setPasswordSha256(Sha256Util.hash("Admin123*"));

        when(userRepository.findByEmail("admin@licodistribuciones.com")).thenReturn(Optional.of(user));

        LoginResponse response = authService.login(new LoginRequest("admin@licodistribuciones.com", "Admin123*"));

        assertTrue(response.authenticated());
    }

    @Test
    void shouldFailAuthenticationWhenPasswordDoesNotMatch() {
        User user = new User();
        user.setEmail("admin@licodistribuciones.com");
        user.setPasswordSha256(Sha256Util.hash("AnotherPassword"));

        when(userRepository.findByEmail("admin@licodistribuciones.com")).thenReturn(Optional.of(user));

        LoginResponse response = authService.login(new LoginRequest("admin@licodistribuciones.com", "Admin123*"));

        assertFalse(response.authenticated());
    }
}
