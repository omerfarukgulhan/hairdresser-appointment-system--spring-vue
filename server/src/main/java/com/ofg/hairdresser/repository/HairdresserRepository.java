package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Hairdresser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HairdresserRepository extends JpaRepository<Hairdresser, Long> {
}
