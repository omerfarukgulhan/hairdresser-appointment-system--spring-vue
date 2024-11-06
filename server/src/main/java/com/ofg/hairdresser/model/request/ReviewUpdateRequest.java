package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequest(
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        int rating,
        @NotBlank(message = "Comment cannot be blank")
        @Size(max = 500, message = "Comment must be under 500 characters")
        String comment
) {

}
