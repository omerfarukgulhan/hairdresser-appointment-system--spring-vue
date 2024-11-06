package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByHairdresserId(Long hairdresserId, Pageable pageable);

    List<Review> findByHairdresser(Hairdresser hairdresser);

    long countByHairdresser(Hairdresser hairdresser);
}
