package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.request.AppointmentCreateRequest;
import com.ofg.hairdresser.model.request.AppointmentUpdateRequest;
import com.ofg.hairdresser.model.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    Page<AppointmentResponse> getAllAppointmentsForUser(long userId, Pageable pageable);

    List<AppointmentResponse> getAllAppointmentsForHairdresserByDate(long hairdresserId, LocalDateTime localDateTime);

    AppointmentResponse bookAppointment(long userId, AppointmentCreateRequest appointmentCreateRequest);

    AppointmentResponse updateAppointment(long userId, long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest);

    AppointmentResponse completeAppointment(long userId, long appointmentId);

    void cancelAppointment(long userId, long appointmentId);
}
