package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentUpdateRequest(
        @NotNull(message = "Appointment date cannot be null")
        @Future(message = "Appointment date must be in the future")
        LocalDateTime appointmentDate
) {

}
