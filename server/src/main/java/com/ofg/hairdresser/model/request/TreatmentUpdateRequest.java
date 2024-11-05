package com.ofg.hairdresser.model.request;

public record TreatmentUpdateRequest(
        String name,
        String description,
        double price,
        int duration
) {

}
