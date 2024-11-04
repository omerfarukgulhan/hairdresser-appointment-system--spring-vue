package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateRequest(
        String oldPassword,
        @Size(min = 8, max = 255) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{app.msg.constraint.password.pattern}")
        String newPassword
) {

}