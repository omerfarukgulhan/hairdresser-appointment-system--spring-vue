package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Hairdresser;

import java.util.List;

public record HairdresserCreateRequest(
        String bio,
        double rating,
        int yearsOfExperience,
        List<String> specialties
) {
    public Hairdresser toHairdresser() {
        Hairdresser hairdresser = new Hairdresser();
        hairdresser.setBio(bio);
        hairdresser.setRating(rating);
        hairdresser.setYearsOfExperience(yearsOfExperience);
        hairdresser.setSpecialties(specialties);
        return hairdresser;
    }
}

