package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.request.AppointmentCreateRequest;
import com.ofg.hairdresser.model.request.AppointmentUpdateRequest;
import com.ofg.hairdresser.model.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    Page<AppointmentResponse> getAllAppointmentsForUser(long userId, Pageable pageable);

    AppointmentResponse bookAppointment(long userId, AppointmentCreateRequest appointmentCreateRequest);

    AppointmentResponse updateAppointment(long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest);

    void cancelAppointment(long appointmentId);
}
