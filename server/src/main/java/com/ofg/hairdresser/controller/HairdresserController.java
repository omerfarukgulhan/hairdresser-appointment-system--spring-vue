package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.core.util.results.ApiResponse;
import com.ofg.hairdresser.model.request.HairdresserCreateRequest;
import com.ofg.hairdresser.model.request.HairdresserUpdateRequest;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import com.ofg.hairdresser.security.CurrentUser;
import com.ofg.hairdresser.service.abstact.HairdresserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hairdressers")
public class HairdresserController {
    private final HairdresserService hairdresserService;

    private static final String HAIRDRESSERS_FETCH_SUCCESS = "app.msg.hairdressers.fetch.success";
    private static final String HAIRDRESSER_FETCH_SUCCESS = "app.msg.hairdresser.fetch.success";
    private static final String HAIRDRESSER_ADD_SUCCESS = "app.msg.hairdresser.add.success";
    private static final String HAIRDRESSER_UPDATE_SUCCESS = "app.msg.hairdresser.update.success";
    private static final String HAIRDRESSER_DELETE_SUCCESS = "app.msg.hairdresser.delete.success";

    @Autowired
    public HairdresserController(HairdresserService hairdresserService) {
        this.hairdresserService = hairdresserService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<HairdresserResponse>>> getAllHairdressers(Pageable pageable) {
        Page<HairdresserResponse> hairdressers = hairdresserService.getAllHairdressers(pageable);
        return ResponseUtil.createApiDataResponse(hairdressers, HAIRDRESSERS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<HairdresserResponse>> getHairdresserById(@PathVariable long hairdresserId) {
        HairdresserResponse hairdresser = hairdresserService.getHairdresserResponseById(hairdresserId);
        return ResponseUtil.createApiDataResponse(hairdresser, HAIRDRESSER_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<HairdresserResponse>> addHairdresser(@AuthenticationPrincipal CurrentUser currentUser,
                                                                               @Valid @RequestBody HairdresserCreateRequest hairdresserCreateRequest) {
        HairdresserResponse hairdresser = hairdresserService.addHairdresser(currentUser.getId(), hairdresserCreateRequest);
        return ResponseUtil.createApiDataResponse(hairdresser, HAIRDRESSER_ADD_SUCCESS, HttpStatus.CREATED);
    }

    @PutMapping("/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<HairdresserResponse>> updateHairdresser(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                  @Valid @RequestBody HairdresserUpdateRequest hairdresserUpdateRequest,
                                                                                  @PathVariable long hairdresserId) {
        HairdresserResponse hairdresser = hairdresserService.updateHairdresser(currentUser.getId(), hairdresserId, hairdresserUpdateRequest);
        return ResponseUtil.createApiDataResponse(hairdresser, HAIRDRESSER_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{hairdresserId}")
    public ResponseEntity<ApiResponse> deleteHairdresser(@AuthenticationPrincipal CurrentUser currentUser,
                                                         @PathVariable long hairdresserId) {
        hairdresserService.deleteHairdresser(currentUser.getId(), hairdresserId);
        return ResponseUtil.createApiResponse(HAIRDRESSER_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
