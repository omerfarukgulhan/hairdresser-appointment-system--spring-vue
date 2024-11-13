package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.response.HairdresserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    Page<HairdresserResponse> getAllInactiveHairdressers(Pageable pageable);

    HairdresserResponse activateHairdresser(long hairdresserId);
}
