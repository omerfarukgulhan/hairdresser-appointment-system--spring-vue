package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.AppointmentUnavailableException;
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

import java.time.LocalDateTime;
import java.util.List;

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

        LocalDateTime startTime = appointmentCreateRequest.appointmentDate();
        LocalDateTime endTime = startTime.plusMinutes(treatment.getDuration());

        validateAppointmentTime(hairdresser, startTime, endTime);

        Appointment appointment = createAppointment(user, hairdresser, treatment, appointmentCreateRequest);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new AppointmentResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse updateAppointment(long userId, long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment existingAppointment = getAuthorizedAppointment(userId, appointmentId);

        LocalDateTime startTime = existingAppointment.getAppointmentDate();
        LocalDateTime endTime = startTime.plusMinutes(existingAppointment.getTreatment().getDuration());
        validateAppointmentTime(existingAppointment.getHairdresser(), startTime, endTime);

        existingAppointment.setAppointmentDate(appointmentUpdateRequest.appointmentDate());
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return new AppointmentResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse completeAppointment(long userId, long appointmentId) {
        Appointment existingAppointment = getAuthorizedAppointment(userId, appointmentId);
        existingAppointment.setCompleted(true);
        Appointment savedAppointment = appointmentRepository.save(existingAppointment);
        return new AppointmentResponse(savedAppointment);
    }

    @Override
    public void cancelAppointment(long userId, long appointmentId) {
        getAuthorizedAppointment(userId, appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

    private Appointment getAuthorizedAppointment(long userId, long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(appointmentId));
        if (appointment.getUser().getId() != userId) {
            throw new UnauthorizedException();
        }
        return appointment;
    }

    private void validateAppointmentTime(Hairdresser hairdresser, LocalDateTime startTime, LocalDateTime endTime) {
        if (isOverlappingWithPastAppointment(hairdresser, startTime)) {
            throw new AppointmentUnavailableException();
        }
        if (isOverlappingWithFutureAppointment(hairdresser, startTime, endTime)) {
            throw new AppointmentUnavailableException();
        }
    }

    private boolean isOverlappingWithPastAppointment(Hairdresser hairdresser, LocalDateTime startTime) {
        List<Appointment> closestPastAppointments = appointmentRepository.findClosestPastAppointment(hairdresser, startTime);
        if (!closestPastAppointments.isEmpty()) {
            Appointment pastAppointment = closestPastAppointments.get(0);
            Treatment pastTreatment = pastAppointment.getTreatment();
            LocalDateTime pastEndTime = pastAppointment.getAppointmentDate().plusMinutes(pastTreatment.getDuration());

            return pastEndTime.isAfter(startTime);
        }
        return false;
    }

    private boolean isOverlappingWithFutureAppointment(Hairdresser hairdresser, LocalDateTime startTime, LocalDateTime endTime) {
        return appointmentRepository.existsOverlappingAppointments(hairdresser, startTime, endTime);
    }

    private Appointment createAppointment(User user, Hairdresser hairdresser, Treatment treatment, AppointmentCreateRequest appointmentCreateRequest) {
        Appointment appointment = appointmentCreateRequest.toAppointment(hairdresser, treatment);
        appointment.setUser(user);
        return appointment;
    }
}
