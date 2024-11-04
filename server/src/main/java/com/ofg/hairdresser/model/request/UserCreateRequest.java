package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "{app.constraint.first-name.not-blank}")
        @Size(min = 4, max = 255)
        String firstName,
        @NotBlank(message = "{app.constraint.last-name.not-blank}")
        @Size(min = 4, max = 255)
        String lastName,
        @NotBlank
        @Email
        @UniqueEmail
        String email,
        @Size(min = 8, max = 255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{app.constraint.password.pattern}")
        String password
) {
    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        return user;
    }
}