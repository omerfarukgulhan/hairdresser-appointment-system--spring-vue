package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.User;

public record UsersListResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String profileImage
) {
    public UsersListResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getProfileImage());
    }
}
