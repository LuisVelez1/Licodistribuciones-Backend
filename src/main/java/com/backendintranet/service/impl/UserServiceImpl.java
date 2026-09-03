package com.backendintranet.service.impl;


import com.backendintranet.dto.request.AdminUpdateUserRequest;
import com.backendintranet.dto.request.RegisterRequest;
import com.backendintranet.dto.request.UpdateUserRequest;
import com.backendintranet.dto.response.UserResponse;
import com.backendintranet.entity.*;
import com.backendintranet.repository.*;
import com.backendintranet.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DirectoryUserRepository directoryRepository;
    private final DirectoryUserAllRepository directoryUserAllRepository;
    private final BirthdayUserRepository birthdayRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<DirectoryUser> getDirectory() {
        return directoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectoryUserAll> getAllDirectory() {
        return directoryUserAllRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirthdayUser> getBirthdays() {
        return birthdayRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getProfile(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToResponse(user);
    }

    @Transactional
        public void changePassword(String id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse updateMyProfile(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow();

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getCedula() != null && !request.getCedula().isBlank()) {
            if (!request.getCedula().equals(user.getCedula()) && userRepository.existsByCedula(request.getCedula())) {
                throw new RuntimeException("La cédula ya está registrada por otro usuario.");
            }
            user.setCedula(request.getCedula());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("El email ya está registrado por otro usuario.");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getBirthDate() != null) user.setBirthDate(request.getBirthDate());
        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateByAdmin(String id, AdminUpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow();

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getCedula() != null && !request.getCedula().isBlank()) {
            if (!request.getCedula().equals(user.getCedula()) && userRepository.existsByCedula(request.getCedula())) {
                throw new RuntimeException("La cédula ya está registrada por otro usuario.");
            }
            user.setCedula(request.getCedula());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("El email ya está registrado por otro usuario.");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getPosition() != null) user.setPosition(request.getPosition());
        if (request.getSede() != null) user.setSede(request.getSede());
        if (request.getAreaId() != null) user.setAreaId(request.getAreaId());
        if (request.getStatus() != null) user.setStatus(request.getStatus());
        if (request.getBirthDate() != null) user.setBirthDate(request.getBirthDate());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (request.getCedula() != null && userRepository.existsByCedula(request.getCedula())) {
            throw new RuntimeException("La cédula ya está registrada");
        }

        String first = request.getFirstName().trim().split("\\s+")[0].toUpperCase();
        String last = request.getLastName().trim().split("\\s+")[0].toUpperCase();
        String baseUsername = first + "." + last;

        String Username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(Username)) {
            Username = baseUsername + counter;
            counter++;
        }

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado"));

        User user = User.builder()
                .username(Username)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cedula(request.getCedula())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .position(request.getPosition())
                .sede(request.getSede())
                .areaId(request.getAreaId())
                .birthDate(request.getBirthDate())
                .status("ACTIVE")
                .roles(new HashSet<>(Set.of(defaultRole)))
                .build();

        return mapToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse changeStatus(String id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("INACTIVE")) {
            throw new RuntimeException("Estado no válido. Use ACTIVE o INACTIVE");
        }

        user.setStatus(status.toUpperCase());
        return mapToResponse(userRepository.save(user));
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .cedula(user.getCedula())
                .position(user.getPosition())
                .sede(user.getSede())
                .status(user.getStatus())
                .areaId(user.getAreaId())
                .birthDate(user.getBirthDate())
                .createdAt(user.getCreatedAt())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
