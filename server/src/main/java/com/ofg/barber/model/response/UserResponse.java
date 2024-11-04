package com.ofg.barber.model.response;

import com.ofg.barber.model.entity.Role;
import com.ofg.barber.model.entity.User;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String profileImage,
        boolean isActive,
        Set<Role> roles
) {
    public UserResponse(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getProfileImage(), user.isActive(), user.getRoles());
    }
}