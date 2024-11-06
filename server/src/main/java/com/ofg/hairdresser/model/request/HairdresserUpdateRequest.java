package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record HairdresserUpdateRequest(
        @NotBlank(message = "Bio cannot be blank")
        @Size(max = 500, message = "Bio must be under 500 characters")
        String bio,
        @Min(value = 0, message = "Years of experience cannot be negative")
        int yearsOfExperience,
        @Size(min = 1, message = "At least one specialty is required")
        List<String> specialties
) {

}
