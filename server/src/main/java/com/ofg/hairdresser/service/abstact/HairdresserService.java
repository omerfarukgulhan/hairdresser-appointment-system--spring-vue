package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.request.HairdresserCreateRequest;
import com.ofg.hairdresser.model.request.HairdresserUpdateRequest;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HairdresserService {
    Page<HairdresserResponse> getAllHairdressers(Pageable pageable);

    HairdresserResponse getHairdresserByUserId(long userId);

    HairdresserResponse getHairdresserResponseById(long hairdresserId);

    Hairdresser getHairdresserEntityById(long hairdresserId);

    HairdresserResponse addHairdresser(long userId, HairdresserCreateRequest hairdresserCreateRequest);

    HairdresserResponse updateHairdresser(long hairdresserId, HairdresserUpdateRequest hairdresserUpdateRequest);

    void deleteHairdresser(long hairdresserId);
}
