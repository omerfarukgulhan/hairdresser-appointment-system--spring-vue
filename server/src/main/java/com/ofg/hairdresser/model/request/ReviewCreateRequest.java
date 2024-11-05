package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Review;

public record ReviewCreateRequest(
        long hairdresserId,
        int rating,
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

