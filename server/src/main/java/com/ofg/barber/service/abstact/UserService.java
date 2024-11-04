package com.ofg.barber.service.abstact;

import com.ofg.barber.model.entity.User;
import com.ofg.barber.model.request.*;
import com.ofg.barber.model.response.UserResponse;
import com.ofg.barber.model.response.UsersListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Page<UsersListResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(long userId);

    User getUserByEmail(String email);

    UserResponse addUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(long userId, UserUpdateRequest userUpdateRequest, MultipartFile file);

    UserResponse activateUser(String token);

    void updatePassword(long userId, UserPasswordUpdateRequest userPasswordUpdateRequest);

    void resetPassword(UserPasswordResetRequest userPasswordResetRequest);

    void setPassword(String token, UserPasswordSetRequest userPasswordSetRequest);

    void deleteUser(long userId);
}