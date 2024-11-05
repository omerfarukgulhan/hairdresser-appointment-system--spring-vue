package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Hairdresser;

import java.util.List;

public record HairdresserResponse(
        Long id,
        Long userId,
        String bio,
        double rating,
        int yearsOfExperience,
        List<String> specialties
) {
    public HairdresserResponse(Hairdresser hairdresser) {
        this(hairdresser.getId(), hairdresser.getUser().getId(),
                hairdresser.getBio(), hairdresser.getRating(),
                hairdresser.getYearsOfExperience(), hairdresser.getSpecialties());
    }
}
