package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Role;
import com.ofg.hairdresser.model.entity.User;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String profileImage,
        boolean isActive,
        Set<Role> roles
) {
    public UserResponse(User user) {
        this(user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), user.getProfileImage(),
                user.isActive(), user.getRoles());
    }
}