package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Hairdresser;
import jakarta.validation.constraints.*;

import java.util.List;

public record HairdresserCreateRequest(
        @NotBlank(message = "Bio cannot be blank")
        @Size(max = 500, message = "Bio must be under 500 characters")
        String bio,
        @Min(value = 0, message = "Years of experience cannot be negative")
        int yearsOfExperience,
        @NotNull(message = "Specialties cannot be null")
        @Size(min = 1, message = "At least one specialty is required")
        List<String> specialties
) {
    public Hairdresser toHairdresser() {
        Hairdresser hairdresser = new Hairdresser();
        hairdresser.setBio(bio);
        hairdresser.setYearsOfExperience(yearsOfExperience);
        hairdresser.setSpecialties(specialties);
        return hairdresser;
    }
}

