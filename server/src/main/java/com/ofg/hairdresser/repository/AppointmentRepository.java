package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
