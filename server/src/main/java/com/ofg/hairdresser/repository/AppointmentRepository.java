package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByUserId(Long userId, Pageable pageable);
}
