package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        Long hairdresserId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {
    public ReviewResponse(Review review) {
        this(review.getId(), review.getUser().getId(),
                review.getHairdresser().getId(), review.getRating(),
                review.getComment(), review.getCreatedAt());
    }
}
