package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.model.response.TimeSlotResponse;
import com.ofg.hairdresser.service.abstact.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    private final static String DAILY_AVAILABILITIES_FETCH_SUCCESS = "app.msg.daily.availabilities.fetch.success";

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<List<TimeSlotResponse>>> getAllAppointmentsForHairdresserByDate(@PathVariable long hairdresserId,
                                                                                                          @RequestParam("date") String date) {
        List<TimeSlotResponse> timeSlotResponses = availabilityService.getDailyAvailabilityForHairdresser(hairdresserId, LocalDate.parse(date));
        return ResponseUtil.createApiDataResponse(timeSlotResponses, DAILY_AVAILABILITIES_FETCH_SUCCESS, HttpStatus.OK);
    }
}
