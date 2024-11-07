package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long userId,
        Long hairdresserId,
        Long treatmentId,
        LocalDateTime appointmentDate,
        boolean completed
) {
    public AppointmentResponse(Appointment appointment) {
        this(appointment.getId(), appointment.getUser().getId(),
                appointment.getHairdresser().getId(), appointment.getTreatment().getId(),
                appointment.getAppointmentDate(), appointment.isCompleted());
    }
}
