package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import com.ofg.hairdresser.service.abstact.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final static String HAIRDRESSERS_FETCH_SUCCESS = "app.msg.hairdressers.fetch.success";
    private final static String HAIRDRESSER_ACTIVATED = "app.msg.hairdressers.activate.success";

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hairdressers/inactives")
    public ResponseEntity<ApiDataResponse<Page<HairdresserResponse>>> getAllInactiveHairdressers(Pageable pageable) {
        Page<HairdresserResponse> hairdressers = adminService.getAllInactiveHairdressers(pageable);
        return ResponseUtil.createApiDataResponse(hairdressers, HAIRDRESSERS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/approve/hairdressers/{hairdresserId}")
    public ResponseEntity<ApiDataResponse<HairdresserResponse>> activateHairdresser(@PathVariable long hairdresserId) {
        HairdresserResponse hairdresser = adminService.activateHairdresser(hairdresserId);
        return ResponseUtil.createApiDataResponse(hairdresser, HAIRDRESSER_ACTIVATED, HttpStatus.OK);
    }
}
