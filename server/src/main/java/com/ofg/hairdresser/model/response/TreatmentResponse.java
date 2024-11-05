package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Treatment;

public record TreatmentResponse(
        Long id,
        Long hairdresserId,
        String name,
        String description,
        double price,
        int duration
) {
    public TreatmentResponse(Treatment treatment) {
        this(treatment.getId(), treatment.getHairdresser().getId(),
                treatment.getName(), treatment.getDescription(),
                treatment.getPrice(), treatment.getDuration());
    }
}
