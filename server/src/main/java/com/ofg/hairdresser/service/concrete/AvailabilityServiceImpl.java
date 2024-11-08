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
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AppointmentService appointmentService;
    private final TreatmentService treatmentService;

    private final LocalTime WORK_DAY_START = LocalTime.of(9, 0);
    private final LocalTime WORK_DAY_END = LocalTime.of(17, 0);
    private final Duration SLOT_DURATION = Duration.ofHours(1);

    @Autowired
    public AvailabilityServiceImpl(AppointmentService appointmentService, TreatmentService treatmentService) {
        this.appointmentService = appointmentService;
        this.treatmentService = treatmentService;
    }

    @Override
    public List<TimeSlotResponse> getDailyAvailabilityForHairdresser(long hairdresserId, LocalDate localDate) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        List<AppointmentResponse> appointments = appointmentService.getAllAppointmentsForHairdresserByDate(hairdresserId, startOfDay);
        List<TimeSlotResponse> schedule = new ArrayList<>();
        LocalTime currentTime = WORK_DAY_START;
        while (currentTime.isBefore(WORK_DAY_END)) {
            LocalTime slotEndTime = currentTime.plus(SLOT_DURATION);
            schedule.add(new TimeSlotResponse(currentTime, slotEndTime, false));
            currentTime = slotEndTime;
        }

        for (AppointmentResponse appointment : appointments) {
            LocalTime appointmentStart = appointment.appointmentDate().toLocalTime();
            Treatment treatment = treatmentService.getTreatmentEntityById(appointment.treatmentId());
            int treatmentDurationInMinutes = treatment.getDuration();
            LocalTime appointmentEnd = appointmentStart.plusMinutes(treatmentDurationInMinutes);
            for (LocalTime time = WORK_DAY_START; time.isBefore(WORK_DAY_END); time = time.plus(SLOT_DURATION)) {
                if (time.isBefore(appointmentEnd) && time.plus(SLOT_DURATION).isAfter(appointmentStart)) {
                    LocalTime finalTime = time;
                    schedule = schedule.stream()
                            .map(slot -> {
                                if (slot.start().equals(finalTime)) {
                                    return new TimeSlotResponse(slot.start(), slot.end(), true);
                                }
                                return slot;
                            })
                            .collect(Collectors.toList());
                }
            }
        }
        return schedule;
    }
}
