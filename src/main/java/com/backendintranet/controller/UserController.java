package com.backendintranet.controller;

import com.backendintranet.dto.request.AdminUpdateUserRequest;
import com.backendintranet.dto.request.ChangePasswordRequest;
import com.backendintranet.dto.request.RegisterRequest;
import com.backendintranet.dto.request.UpdateUserRequest;
import com.backendintranet.dto.response.UserResponse;
import com.backendintranet.entity.BirthdayUser;
import com.backendintranet.entity.DirectoryUser;
import com.backendintranet.entity.DirectoryUserAll;
import com.backendintranet.entity.User;
import com.backendintranet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/directory")
    public ResponseEntity<List<DirectoryUser>> getDirectory() {
        return ResponseEntity.ok(userService.getDirectory());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/directoryAll")
    public ResponseEntity<List<DirectoryUserAll>> getDirectoryAll() {return ResponseEntity.ok(userService.getAllDirectory());}

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/birthdays")
    public ResponseEntity<List<BirthdayUser>> getBirthdays() {
        return ResponseEntity.ok(userService.getBirthdays());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable String id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profile/update")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateMyProfile(user.getId(), request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{id}/admin-update")
    public ResponseEntity<UserResponse> updateByAdmin(
            @PathVariable String id,
            @RequestBody AdminUpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateByAdmin(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> changeStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(userService.changeStatus(id, status));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getProfile(user.getId()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable String id,
            @RequestBody ChangePasswordRequest request) {

        userService.changePassword(id, request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
