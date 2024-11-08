package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Hairdresser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HairdresserRepository extends JpaRepository<Hairdresser, Long> {
    @Query("SELECT h FROM Hairdresser h WHERE h.active = true")
    Page<Hairdresser> findAllActiveHairdresser(Pageable pageable);

    @Query("SELECT h FROM Hairdresser h WHERE h.id = :hairdresserId AND h.active = true")
    Optional<Hairdresser> findActiveById(Long hairdresserId);

    @Query("SELECT h FROM Hairdresser h WHERE h.id = :hairdresserId AND h.active = false")
    Optional<Hairdresser> findInactiveById(Long hairdresserId);

    @Query("SELECT h FROM Hairdresser h WHERE h.user.id = :userId AND h.active = true")
    Optional<Hairdresser> findActiveByUserId(Long userId);
}
