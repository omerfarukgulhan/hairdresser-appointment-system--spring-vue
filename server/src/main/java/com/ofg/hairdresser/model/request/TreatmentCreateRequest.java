package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Treatment;

public record TreatmentCreateRequest(
        String name,
        String description,
        double price,
        int duration
) {
    public Treatment toTreatment() {
        Treatment treatment = new Treatment();
        treatment.setName(name);
        treatment.setDescription(description);
        treatment.setPrice(price);
        treatment.setDuration(duration);
        return treatment;
    }
}

