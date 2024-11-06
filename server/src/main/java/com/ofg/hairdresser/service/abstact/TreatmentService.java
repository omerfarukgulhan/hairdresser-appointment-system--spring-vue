package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.entity.Treatment;
import com.ofg.hairdresser.model.request.TreatmentCreateRequest;
import com.ofg.hairdresser.model.request.TreatmentUpdateRequest;
import com.ofg.hairdresser.model.response.TreatmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreatmentService {
    Page<TreatmentResponse> getAllTreatmentsForHairdresser(long hairdresserId, Pageable pageable);

    TreatmentResponse getTreatmentResponseById(long treatmentId);

    Treatment getTreatmentEntityById(long treatmentId);

    TreatmentResponse addTreatment(long userId, TreatmentCreateRequest treatmentCreateRequest);

    TreatmentResponse updateTreatment(long userId, long treatmentId, TreatmentUpdateRequest treatmentUpdateRequest);

    void deleteTreatment(long userId, long treatmentId);
}
