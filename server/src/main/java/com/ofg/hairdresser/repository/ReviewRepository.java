package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
