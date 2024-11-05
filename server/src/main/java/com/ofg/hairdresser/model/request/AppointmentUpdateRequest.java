package com.ofg.hairdresser.model.request;

import java.time.LocalDateTime;

public record AppointmentUpdateRequest(
        LocalDateTime appointmentDate,
        String status
) {

}
