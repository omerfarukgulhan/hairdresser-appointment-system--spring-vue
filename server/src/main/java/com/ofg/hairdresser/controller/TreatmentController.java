package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.core.util.results.ApiResponse;
import com.ofg.hairdresser.model.request.TreatmentCreateRequest;
import com.ofg.hairdresser.model.request.TreatmentUpdateRequest;
import com.ofg.hairdresser.model.response.TreatmentResponse;
import com.ofg.hairdresser.security.CurrentUser;
import com.ofg.hairdresser.service.abstact.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;

    private static final String TREATMENTS_FETCH_SUCCESS = "app.msg.treatments.fetch.success";
    private static final String TREATMENT_FETCH_SUCCESS = "app.msg.treatment.fetch.success";
    private static final String TREATMENT_ADD_SUCCESS = "app.msg.treatment.add.success";
    private static final String TREATMENT_UPDATE_SUCCESS = "app.msg.treatment.update.success";
    private static final String TREATMENT_DELETE_SUCCESS = "app.msg.treatment.delete.success";

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping("hairdresser/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<Page<TreatmentResponse>>> getAllTreatmentsForHairdresser(@PathVariable long hairdresserId,
                                                                                                   Pageable pageable) {
        Page<TreatmentResponse> treatments = treatmentService.getAllTreatmentsForHairdresser(hairdresserId, pageable);
        return ResponseUtil.createApiDataResponse(treatments, TREATMENTS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{treatmentId}")
    public ResponseEntity<ApiDataResponse<TreatmentResponse>> getTreatmentById(@PathVariable long treatmentId) {
        TreatmentResponse treatment = treatmentService.getTreatmentResponseById(treatmentId);
        return ResponseUtil.createApiDataResponse(treatment, TREATMENT_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<TreatmentResponse>> addTreatment(@AuthenticationPrincipal CurrentUser currentUser,
                                                                             @Valid @RequestBody TreatmentCreateRequest treatmentCreateRequest) {
        TreatmentResponse treatment = treatmentService.addTreatment(currentUser.getId(), treatmentCreateRequest);
        return ResponseUtil.createApiDataResponse(treatment, TREATMENT_ADD_SUCCESS, HttpStatus.CREATED);
    }

    @PutMapping("/{treatmentId}")
    public ResponseEntity<ApiDataResponse<TreatmentResponse>> updateTreatment(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                @Valid @RequestBody TreatmentUpdateRequest treatmentUpdateRequest,
                                                                                @PathVariable long treatmentId) {
        TreatmentResponse treatment = treatmentService.updateTreatment(currentUser.getId(), treatmentId, treatmentUpdateRequest);
        return ResponseUtil.createApiDataResponse(treatment, TREATMENT_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{treatmentId}")
    public ResponseEntity<ApiResponse> deleteTreatment(@AuthenticationPrincipal CurrentUser currentUser,
                                                         @PathVariable long treatmentId) {
        treatmentService.deleteTreatment(currentUser.getId(), treatmentId);
        return ResponseUtil.createApiResponse(TREATMENT_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
