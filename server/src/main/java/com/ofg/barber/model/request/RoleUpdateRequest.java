package com.ofg.barber.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleUpdateRequest(
        @NotBlank(message = "Role name is required")
        @Size(min = 1, max = 100, message = "Role name must be between 1 and 100 characters")
        String name
) {

}