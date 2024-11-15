package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        String userName,
        Long hairdresserId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {
    public ReviewResponse(Review review) {
        this(review.getId(), review.getUser().getId(),
                review.getUser().getFirstName() + " " +review.getUser().getLastName(),
                review.getHairdresser().getId(), review.getRating(),
                review.getComment(), review.getCreatedAt());
    }
}
