package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Appointment;
import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Treatment;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentCreateRequest(
        @NotNull(message = "Hairdresser ID cannot be null")
        Long hairdresserId,
        @NotNull(message = "Treatment ID cannot be null")
        Long treatmentId,
        @NotNull(message = "Appointment date cannot be null")
        @Future(message = "Appointment date must be in the future")
        LocalDateTime appointmentDate
) {
    public Appointment toAppointment(Hairdresser hairdresser, Treatment treatment) {
        Appointment appointment = new Appointment();
        appointment.setHairdresser(hairdresser);
        appointment.setTreatment(treatment);
        appointment.setAppointmentDate(appointmentDate);
        return appointment;
    }
}

