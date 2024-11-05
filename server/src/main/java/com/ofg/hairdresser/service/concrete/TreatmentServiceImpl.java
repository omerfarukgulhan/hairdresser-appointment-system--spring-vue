package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.general.NotFoundException;
import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.Treatment;
import com.ofg.hairdresser.model.request.TreatmentCreateRequest;
import com.ofg.hairdresser.model.request.TreatmentUpdateRequest;
import com.ofg.hairdresser.model.response.TreatmentResponse;
import com.ofg.hairdresser.repository.TreatmentRepository;
import com.ofg.hairdresser.service.abstact.HairdresserService;
import com.ofg.hairdresser.service.abstact.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final HairdresserService hairdresserService;

    @Autowired
    public TreatmentServiceImpl(HairdresserService hairdresserService, TreatmentRepository treatmentRepository) {
        this.hairdresserService = hairdresserService;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public Page<TreatmentResponse> getAllTreatments(Pageable pageable) {
        return treatmentRepository.findAll(pageable).map(TreatmentResponse::new);
    }

    @Override
    public TreatmentResponse getTreatmentResponseById(long treatmentId) {
        return treatmentRepository.findById(treatmentId)
                .map(TreatmentResponse::new)
                .orElseThrow(() -> new NotFoundException(treatmentId));
    }

    @Override
    public Treatment getTreatmentEntityById(long treatmentId) {
        return treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new NotFoundException(treatmentId));
    }

    @Override
    public TreatmentResponse addTreatment(long hairdresserId, TreatmentCreateRequest treatmentCreateRequest) {
        Treatment treatment = treatmentCreateRequest.toTreatment();
        Hairdresser hairdresser = hairdresserService.getHairdresserEntityById(hairdresserId);
        treatment.setHairdresser(hairdresser);
        Treatment savedTreatment = treatmentRepository.save(treatment);
        return new TreatmentResponse(savedTreatment);
    }

    @Override
    public TreatmentResponse updateTreatment(long treatmentId, TreatmentUpdateRequest treatmentUpdateRequest) {
        Treatment existingTreatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new NotFoundException(treatmentId));
        updateTreatmentDetails(existingTreatment, treatmentUpdateRequest);
        Treatment updatedTreatment = treatmentRepository.save(existingTreatment);
        return new TreatmentResponse(updatedTreatment);
    }

    @Override
    public void deleteTreatment(long treatmentId) {
        treatmentRepository.deleteById(treatmentId);
    }

    private void updateTreatmentDetails(Treatment treatment, TreatmentUpdateRequest treatmentUpdateRequest) {
        treatment.setName(treatmentUpdateRequest.name());
        treatment.setDescription(treatmentUpdateRequest.description());
        treatment.setPrice(treatmentUpdateRequest.price());
        treatment.setDuration(treatmentUpdateRequest.duration());
    }
}
