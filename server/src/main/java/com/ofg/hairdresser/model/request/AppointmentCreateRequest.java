package com.ofg.hairdresser.model.request;

import com.ofg.hairdresser.model.entity.Appointment;
import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Treatment;

import java.time.LocalDateTime;

public record AppointmentCreateRequest(
        Long hairdresserId,
        Long treatmentId,
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

