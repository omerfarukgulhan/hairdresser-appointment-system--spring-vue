package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.request.ReviewCreateRequest;
import com.ofg.hairdresser.model.request.ReviewUpdateRequest;
import com.ofg.hairdresser.model.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewResponse> getAllReviewsForHairdresser(long hairdresserId, Pageable pageable);

    ReviewResponse addReview(long userId, ReviewCreateRequest reviewCreateRequest);

    ReviewResponse updateReview(long userId, long reviewId, ReviewUpdateRequest reviewUpdateRequest);

    void deleteReview(long userId, long reviewId);
}
