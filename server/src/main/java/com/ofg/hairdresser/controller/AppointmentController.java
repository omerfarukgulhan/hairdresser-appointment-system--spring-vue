package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.core.util.results.ApiResponse;
import com.ofg.hairdresser.model.request.AppointmentCreateRequest;
import com.ofg.hairdresser.model.request.AppointmentUpdateRequest;
import com.ofg.hairdresser.model.response.AppointmentResponse;
import com.ofg.hairdresser.security.CurrentUser;
import com.ofg.hairdresser.service.abstact.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    private static final String APPOINTMENTS_FETCH_SUCCESS = "app.msg.appointments.fetch.success";
    private static final String APPOINTMENT_BOOK_SUCCESS = "app.msg.appointment.book.success";
    private static final String APPOINTMENT_UPDATE_SUCCESS = "app.msg.appointment.update.success";
    private static final String APPOINTMENT_CANCEL_SUCCESS = "app.msg.appointment.cancel.success";

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<AppointmentResponse>>> getAllAppointmentsForUser(Pageable pageable,
                                                                                                @AuthenticationPrincipal CurrentUser currentUser) {
        Page<AppointmentResponse> appointments = appointmentService.getAllAppointmentsForUser(currentUser.getId(), pageable);
        return ResponseUtil.createApiDataResponse(appointments, APPOINTMENTS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<AppointmentResponse>> bookAppointment(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                @Valid @RequestBody AppointmentCreateRequest appointmentCreateRequest) {
        AppointmentResponse appointment = appointmentService.bookAppointment(currentUser.getId(), appointmentCreateRequest);
        return ResponseUtil.createApiDataResponse(appointment, APPOINTMENT_BOOK_SUCCESS, HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<ApiDataResponse<AppointmentResponse>> updateAppointment(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                  @Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest,
                                                                                  @PathVariable long appointmentId) {
        AppointmentResponse appointment = appointmentService.updateAppointment(currentUser.getId(), appointmentId, appointmentUpdateRequest);
        return ResponseUtil.createApiDataResponse(appointment, APPOINTMENT_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/complete/{appointmentId}")
    public ResponseEntity<ApiDataResponse<AppointmentResponse>> completeAppointment(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                    @PathVariable long appointmentId) {
        AppointmentResponse appointment = appointmentService.completeAppointment(currentUser.getId(), appointmentId);
        return ResponseUtil.createApiDataResponse(appointment, APPOINTMENT_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse> deleteAppointment(@AuthenticationPrincipal CurrentUser currentUser,
                                                         @PathVariable long appointmentId) {
        appointmentService.cancelAppointment(currentUser.getId(), appointmentId);
        return ResponseUtil.createApiResponse(APPOINTMENT_CANCEL_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
