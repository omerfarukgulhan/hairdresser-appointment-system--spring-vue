package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TreatmentUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be under 100 characters")
        String name,
        @NotBlank(message = "Description cannot be blank")
        @Size(max = 500, message = "Description must be under 500 characters")
        String description,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        double price,
        @Min(value = 1, message = "Duration must be at least 1 minute")
        int duration
) {

}
