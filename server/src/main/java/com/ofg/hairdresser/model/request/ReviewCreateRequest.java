package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Review;

public record ReviewCreateRequest(
        int rating,
        String comment
) {
    public Review toReview() {
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }
}

