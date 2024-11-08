package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.model.entity.Treatment;
import com.ofg.hairdresser.model.response.AppointmentResponse;
import com.ofg.hairdresser.model.response.TimeSlotResponse;
import com.ofg.hairdresser.service.abstact.AppointmentService;
import com.ofg.hairdresser.service.abstact.AvailabilityService;
import com.ofg.hairdresser.service.abstact.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AppointmentService appointmentService;
    private final TreatmentService treatmentService;

    private static final LocalTime WORK_DAY_START = LocalTime.of(9, 0);
    private static final LocalTime WORK_DAY_END = LocalTime.of(17, 0);
    private static final Duration SLOT_DURATION = Duration.ofMinutes(30);

    @Autowired
    public AvailabilityServiceImpl(AppointmentService appointmentService, TreatmentService treatmentService) {
        this.appointmentService = appointmentService;
        this.treatmentService = treatmentService;
    }

    @Override
    public List<TimeSlotResponse> getDailyAvailabilityForHairdresser(long hairdresserId, LocalDate localDate) {
        List<AppointmentResponse> appointments = getAppointmentsForDay(hairdresserId, localDate);
        List<TimeSlotResponse> schedule = generateDailySchedule();

        markBookedSlots(schedule, appointments);

        return schedule;
    }

    private List<AppointmentResponse> getAppointmentsForDay(long hairdresserId, LocalDate localDate) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return appointmentService.getAllAppointmentsForHairdresserByDate(hairdresserId, startOfDay);
    }

    private List<TimeSlotResponse> generateDailySchedule() {
        List<TimeSlotResponse> schedule = new ArrayList<>();
        LocalTime currentTime = WORK_DAY_START;

        while (currentTime.isBefore(WORK_DAY_END)) {
            LocalTime slotEndTime = currentTime.plus(SLOT_DURATION);
            schedule.add(new TimeSlotResponse(currentTime, slotEndTime, false));
            currentTime = slotEndTime;
        }

        return schedule;
    }

    private void markBookedSlots(List<TimeSlotResponse> schedule, List<AppointmentResponse> appointments) {
        for (AppointmentResponse appointment : appointments) {
            LocalTime appointmentStart = appointment.appointmentDate().toLocalTime();
            int treatmentDurationInMinutes = getTreatmentDuration(appointment.treatmentId());
            LocalTime appointmentEnd = appointmentStart.plusMinutes(treatmentDurationInMinutes);

            for (int i = 0; i < schedule.size(); i++) {
                TimeSlotResponse slot = schedule.get(i);
                if (isSlotOverlapping(slot, appointmentStart, appointmentEnd)) {
                    schedule.set(i, new TimeSlotResponse(slot.start(), slot.end(), true));
                }
            }
        }
    }

    private int getTreatmentDuration(long treatmentId) {
        Treatment treatment = treatmentService.getTreatmentEntityById(treatmentId);
        return treatment.getDuration();
    }

    private boolean isSlotOverlapping(TimeSlotResponse slot, LocalTime appointmentStart, LocalTime appointmentEnd) {
        return slot.start().isBefore(appointmentEnd) && slot.end().isAfter(appointmentStart);
    }
}