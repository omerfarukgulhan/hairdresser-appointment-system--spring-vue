package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.authentication.UnauthorizedException;
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
    public TreatmentServiceImpl(HairdresserService hairdresserService,
                                TreatmentRepository treatmentRepository) {
        this.hairdresserService = hairdresserService;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public Page<TreatmentResponse> getAllTreatmentsForHairdresser(long hairdresserId, Pageable pageable) {
        return treatmentRepository.findByHairdresserId(hairdresserId, pageable)
                .map(TreatmentResponse::new);
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
    public TreatmentResponse addTreatment(long userId, TreatmentCreateRequest treatmentCreateRequest) {
        Hairdresser hairdresser = hairdresserService.getHairdresserEntityByUserId(userId);
        Treatment treatment = treatmentCreateRequest.toTreatment();
        treatment.setHairdresser(hairdresser);
        Treatment savedTreatment = treatmentRepository.save(treatment);
        return new TreatmentResponse(savedTreatment);
    }

    @Override
    public TreatmentResponse updateTreatment(long userId, long treatmentId, TreatmentUpdateRequest treatmentUpdateRequest) {
        Treatment existingTreatment = getAndValidateTreatmentOwnership(treatmentId, userId);
        updateTreatmentDetails(existingTreatment, treatmentUpdateRequest);
        Treatment updatedTreatment = treatmentRepository.save(existingTreatment);
        return new TreatmentResponse(updatedTreatment);
    }

    @Override
    public void deleteTreatment(long userId, long treatmentId) {
        getAndValidateTreatmentOwnership(treatmentId, userId);
        treatmentRepository.deleteById(treatmentId);
    }

    private Treatment getAndValidateTreatmentOwnership(long treatmentId, long userId) {
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new NotFoundException(treatmentId));
        long hairdresserId = hairdresserService.getHairdresserEntityByUserId(userId).getId();
        if (treatment.getHairdresser().getId() != hairdresserId) {
            throw new UnauthorizedException();
        }
        return treatment;
    }

    private void updateTreatmentDetails(Treatment treatment, TreatmentUpdateRequest treatmentUpdateRequest) {
        treatment.setName(treatmentUpdateRequest.name());
        treatment.setDescription(treatmentUpdateRequest.description());
        treatment.setPrice(treatmentUpdateRequest.price());
        treatment.setDuration(treatmentUpdateRequest.duration());
    }
}
