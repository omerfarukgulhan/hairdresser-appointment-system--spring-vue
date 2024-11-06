package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Review;
import jakarta.validation.constraints.*;

public record ReviewCreateRequest(
        @NotNull(message = "Hairdresser ID cannot be null")
        long hairdresserId,
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        int rating,
        @NotBlank(message = "Comment cannot be blank")
        @Size(max = 500, message = "Comment must be under 500 characters")
        String comment
) {
    public Review toReview(Hairdresser hairdresser) {
        Review review = new Review();
        review.setHairdresser(hairdresser);
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }
}

