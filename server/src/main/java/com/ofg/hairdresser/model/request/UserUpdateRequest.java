package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @NotBlank(message = "{app.constraint.first-name.not-blank}")
        @Size(min = 4, max = 255)
        String firstName,
        @NotBlank(message = "{app.constraint.last-name.not-blank}")
        @Size(min = 4, max = 255)
        String lastName,
        @NotBlank(message = "{app.constraint.phone-number.not-blank}")
        @Size(min = 10, max = 15, message = "{app.constraint.phone-number.size}")
        String phoneNumber
) {

}