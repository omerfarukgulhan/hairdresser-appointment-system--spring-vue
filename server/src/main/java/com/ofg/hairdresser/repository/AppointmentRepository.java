package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.Appointment;
import com.ofg.hairdresser.model.entity.Hairdresser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.hairdresser = :hairdresser " +
            "AND a.appointmentDate BETWEEN :startTime AND :endTime")
    boolean existsOverlappingAppointments(@Param("hairdresser") Hairdresser hairdresser,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.hairdresser = :hairdresser " +
            "AND a.appointmentDate < :startTime ORDER BY a.appointmentDate DESC")
    List<Appointment> findClosestPastAppointment(@Param("hairdresser") Hairdresser hairdresser,
                                                 @Param("startTime") LocalDateTime startTime);
}
