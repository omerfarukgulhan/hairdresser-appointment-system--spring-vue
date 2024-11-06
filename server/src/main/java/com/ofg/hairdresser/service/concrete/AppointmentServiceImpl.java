package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.authentication.UnauthorizedException;
import com.ofg.hairdresser.exception.general.NotFoundException;
import com.ofg.hairdresser.model.entity.Appointment;
import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Treatment;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.AppointmentCreateRequest;
import com.ofg.hairdresser.model.request.AppointmentUpdateRequest;
import com.ofg.hairdresser.model.response.AppointmentResponse;
import com.ofg.hairdresser.repository.AppointmentRepository;
import com.ofg.hairdresser.service.abstact.AppointmentService;
import com.ofg.hairdresser.service.abstact.HairdresserService;
import com.ofg.hairdresser.service.abstact.TreatmentService;
import com.ofg.hairdresser.service.abstact.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HairdresserService hairdresserService;
    private final TreatmentService treatmentService;
    private final UserService userService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  HairdresserService hairdresserService,
                                  TreatmentService treatmentService,
                                  UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.hairdresserService = hairdresserService;
        this.treatmentService = treatmentService;
        this.userService = userService;
    }

    @Override
    public Page<AppointmentResponse> getAllAppointmentsForUser(long userId, Pageable pageable) {
        return appointmentRepository.findByUserId(userId, pageable)
                .map(AppointmentResponse::new);
    }

    @Override
    public AppointmentResponse bookAppointment(long userId, AppointmentCreateRequest appointmentCreateRequest) {
        User user = userService.getUserEntityById(userId);
        Treatment treatment = treatmentService.getTreatmentEntityById(appointmentCreateRequest.treatmentId());
        Hairdresser hairdresser = hairdresserService.getHairdresserEntityById(appointmentCreateRequest.hairdresserId());
        Appointment appointment = appointmentCreateRequest.toAppointment(hairdresser, treatment);
        appointment.setUser(user);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse updateAppointment(long userId, long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(appointmentId));
        if (existingAppointment.getUser().getId() != userId) {
            throw new UnauthorizedException();
        }
        updateAppointmentDetails(existingAppointment, appointmentUpdateRequest);
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return new AppointmentResponse(savedAppointment);
    }

    @Override
    public void cancelAppointment(long userId, long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(appointmentId));
        if (appointment.getUser().getId() != userId) {
            throw new UnauthorizedException();
        }
        appointmentRepository.deleteById(appointmentId);
    }

    private void updateAppointmentDetails(Appointment appointment, AppointmentUpdateRequest appointmentUpdateRequest) {
        appointment.setAppointmentDate(appointmentUpdateRequest.appointmentDate());
        appointment.setStatus(appointmentUpdateRequest.status());
    }
}
