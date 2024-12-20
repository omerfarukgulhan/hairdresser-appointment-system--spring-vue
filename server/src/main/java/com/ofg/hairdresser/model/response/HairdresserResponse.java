package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Hairdresser;

import java.util.List;
import java.util.stream.Collectors;

public record HairdresserResponse(
        Long id,
        Long userId,
        String shopName,
        String bio,
        int numberOfReviews,
        double averageRating,
        int yearsOfExperience,
        String address,
        List<String> specialties,
        String mainImage,
        List<String> sideImages,
        List<TreatmentResponse> treatments
) {
    public HairdresserResponse(Hairdresser hairdresser) {
        this(hairdresser.getId(), hairdresser.getUser().getId(),
                hairdresser.getShopName(),
                hairdresser.getBio(), hairdresser.getNumberOfReviews(),
                hairdresser.getAverageRating(), hairdresser.getYearsOfExperience(),
                hairdresser.getAddress(), hairdresser.getSpecialties(),
                hairdresser.getMainImage(), hairdresser.getSideImages(),
                hairdresser.getTreatments().stream()
                        .map(TreatmentResponse::new)
                        .collect(Collectors.toList()));
    }
}