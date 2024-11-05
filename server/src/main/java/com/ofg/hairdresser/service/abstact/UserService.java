package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.*;
import com.ofg.hairdresser.model.response.UserResponse;
import com.ofg.hairdresser.model.response.UsersListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Page<UsersListResponse> getAllUsers(Pageable pageable);

    UserResponse getUserResponseById(long userId);

    User getUserEntityById(long userId);

    User getUserByEmail(String email);

    UserResponse addUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(long userId, UserUpdateRequest userUpdateRequest, MultipartFile file);

    UserResponse activateUser(String token);

    void updatePassword(long userId, UserPasswordUpdateRequest userPasswordUpdateRequest);

    void resetPassword(UserPasswordResetRequest userPasswordResetRequest);

    void setPassword(String token, UserPasswordSetRequest userPasswordSetRequest);

    void deleteUser(long userId);
}