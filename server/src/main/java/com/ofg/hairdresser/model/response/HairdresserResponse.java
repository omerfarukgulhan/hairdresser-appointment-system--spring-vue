package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Hairdresser;

import java.util.List;
import java.util.stream.Collectors;

public record HairdresserResponse(
        Long id,
        Long userId,
        String bio,
        double rating,
        int yearsOfExperience,
        List<String> specialties,
        List<TreatmentResponse> treatments // List of TreatmentResponse
) {
    public HairdresserResponse(Hairdresser hairdresser) {
        this(hairdresser.getId(), hairdresser.getUser().getId(),
                hairdresser.getBio(), hairdresser.getRating(),
                hairdresser.getYearsOfExperience(), hairdresser.getSpecialties(),
                hairdresser.getTreatments().stream() // Convert each Treatment to TreatmentResponse
                        .map(TreatmentResponse::new) // Map Treatment entity to TreatmentResponse
                        .collect(Collectors.toList()));
    }
}