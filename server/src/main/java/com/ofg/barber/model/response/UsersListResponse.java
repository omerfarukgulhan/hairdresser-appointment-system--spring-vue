package com.ofg.barber.model.response;

import com.ofg.barber.model.entity.User;

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
