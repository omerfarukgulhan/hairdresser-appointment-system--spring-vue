package com.ofg.hairdresser.controller;


import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.core.util.results.ApiResponse;
import com.ofg.hairdresser.model.request.ReviewCreateRequest;
import com.ofg.hairdresser.model.request.ReviewUpdateRequest;
import com.ofg.hairdresser.model.response.ReviewResponse;
import com.ofg.hairdresser.security.CurrentUser;
import com.ofg.hairdresser.service.abstact.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    private static final String REVIEWS_FETCH_SUCCESS = "app.msg.reviews.fetch.success";
    private static final String REVIEW_ADD_SUCCESS = "app.msg.review.add.success";
    private static final String REVIEW_UPDATE_SUCCESS = "app.msg.review.update.success";
    private static final String REVIEW_DELETE_SUCCESS = "app.msg.review.delete.success";

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<Page<ReviewResponse>>> getAllReviewsForHairdresser(@PathVariable long hairdresserId,
                                                                                             Pageable pageable) {
        Page<ReviewResponse> reviews = reviewService.getAllReviewsForHairdresser(hairdresserId, pageable);
        return ResponseUtil.createApiDataResponse(reviews, REVIEWS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<ReviewResponse>> addReview(@AuthenticationPrincipal CurrentUser currentUser,
                                                                     @Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        ReviewResponse review = reviewService.addReview(currentUser.getId(), reviewCreateRequest);
        return ResponseUtil.createApiDataResponse(review, REVIEW_ADD_SUCCESS, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiDataResponse<ReviewResponse>> updateReview(@AuthenticationPrincipal CurrentUser currentUser,
                                                                        @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                                                        @PathVariable long reviewId) {
        ReviewResponse review = reviewService.updateReview(currentUser.getId(), reviewId, reviewUpdateRequest);
        return ResponseUtil.createApiDataResponse(review, REVIEW_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@AuthenticationPrincipal CurrentUser currentUser,
                                                    @PathVariable long reviewId) {
        reviewService.deleteReview(currentUser.getId(), reviewId);
        return ResponseUtil.createApiResponse(REVIEW_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}