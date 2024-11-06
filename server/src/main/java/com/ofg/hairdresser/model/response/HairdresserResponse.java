package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Hairdresser;

import java.util.List;
import java.util.stream.Collectors;

public record HairdresserResponse(
        Long id,
        Long userId,
        String bio,
        int numberOfReviews,
        double averageRating,
        int yearsOfExperience,
        List<String> specialties,
        List<TreatmentResponse> treatments
) {
    public HairdresserResponse(Hairdresser hairdresser) {
        this(hairdresser.getId(), hairdresser.getUser().getId(),
                hairdresser.getBio(), hairdresser.getNumberOfReviews(), hairdresser.getAverageRating(),
                hairdresser.getYearsOfExperience(), hairdresser.getSpecialties(),
                hairdresser.getTreatments().stream()
                        .map(TreatmentResponse::new)
                        .collect(Collectors.toList()));
    }
}