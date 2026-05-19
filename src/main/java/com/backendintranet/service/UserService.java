package com.backendintranet.service;

import com.backendintranet.dto.request.AdminUpdateUserRequest;
import com.backendintranet.dto.request.RegisterRequest;
import com.backendintranet.dto.request.UpdateUserRequest;
import com.backendintranet.dto.response.UserResponse;
import com.backendintranet.entity.BirthdayUser;
import com.backendintranet.entity.DirectoryUser;
import com.backendintranet.entity.DirectoryUserAll;

import java.util.List;

public interface UserService {
    List<DirectoryUser> getDirectory();
    List<DirectoryUserAll> getAllDirectory();
    List<BirthdayUser> getBirthdays();
    UserResponse getProfile(String id);
    UserResponse updateMyProfile(String id, UpdateUserRequest request);
    UserResponse updateByAdmin(String id, AdminUpdateUserRequest request);
    UserResponse register(RegisterRequest request);
    UserResponse changeStatus(String id, String status);
    void changePassword(String id, String newPassword);
}
