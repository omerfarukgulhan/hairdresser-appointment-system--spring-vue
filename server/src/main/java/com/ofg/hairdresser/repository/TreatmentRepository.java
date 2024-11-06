package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Page<Treatment> findByHairdresserId(long hairdresserId, Pageable pageable);
}
