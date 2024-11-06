package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.response.HairdresserResponse;

public interface AdminService {
    HairdresserResponse activateHairdresser(long hairdresserId);
}
