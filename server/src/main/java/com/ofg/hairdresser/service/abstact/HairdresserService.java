package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.request.HairdresserCreateRequest;
import com.ofg.hairdresser.model.request.HairdresserUpdateRequest;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface HairdresserService {
    Page<HairdresserResponse> getAllHairdressers(Pageable pageable);

    Hairdresser getHairdresserEntityByUserId(long userId);

    HairdresserResponse getHairdresserResponseById(long hairdresserId);

    Hairdresser getHairdresserEntityById(long hairdresserId);

    Hairdresser getInactiveHairdresserEntityById(long hairdresserId);

    HairdresserResponse addHairdresser(long userId, HairdresserCreateRequest hairdresserCreateRequest);

    HairdresserResponse updateHairdresser(long userId, long hairdresserId, HairdresserUpdateRequest hairdresserUpdateRequest);

    HairdresserResponse uploadImages(long userId, long hairdresserId, MultipartFile mainPhoto);

    void deleteHairdresser(long userId, long hairdresserId);
}
