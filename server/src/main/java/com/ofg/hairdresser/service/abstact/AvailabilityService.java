package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.response.TimeSlotResponse;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    List<TimeSlotResponse> getDailyAvailabilityForHairdresser(long hairdresserId, LocalDate localDate);
}
