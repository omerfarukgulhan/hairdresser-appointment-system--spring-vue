package com.ofg.hairdresser.model.response;

import java.time.LocalTime;

public record TimeSlotResponse(LocalTime start, LocalTime end, boolean isBooked) {

}
