package com.ofg.hairdresser.controller;

import com.ofg.hairdresser.core.util.response.ResponseUtil;
import com.ofg.hairdresser.core.util.results.ApiDataResponse;
import com.ofg.hairdresser.core.util.results.ApiResponse;
import com.ofg.hairdresser.model.request.RoleCreateRequest;
import com.ofg.hairdresser.model.request.RoleUpdateRequest;
import com.ofg.hairdresser.model.response.RoleListResponse;
import com.ofg.hairdresser.model.response.RoleResponse;
import com.ofg.hairdresser.service.abstact.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    private static final String ROLES_FETCH_SUCCESS = "app.msg.roles.fetch.success";
    private static final String ROLE_FETCH_SUCCESS = "app.msg.role.fetch.success";
    private static final String ROLE_CREATE_SUCCESS = "app.msg.role.create.success";
    private static final String ROLE_UPDATE_SUCCESS = "app.msg.role.update.success";
    private static final String ROLE_DELETE_SUCCESS = "app.msg.role.delete.success";

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<RoleListResponse>>> getAllRoles(Pageable pageable) {
        Page<RoleListResponse> roles = roleService.getAllRoles(pageable);
        return ResponseUtil.createApiDataResponse(roles, ROLES_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ApiDataResponse<RoleResponse>> getRoleById(@PathVariable long roleId) {
        RoleResponse roleResponse = roleService.getRoleById(roleId);
        return ResponseUtil.createApiDataResponse(roleResponse, ROLE_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RoleResponse>> createRole(@Valid @RequestBody RoleCreateRequest roleCreateRequest) {
        RoleResponse roleResponse = roleService.addRole(roleCreateRequest);
        return ResponseUtil.createApiDataResponse(roleResponse, ROLE_CREATE_SUCCESS, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiDataResponse<RoleResponse>> updateRole(@PathVariable long roleId,
                                                                    @Valid @RequestBody RoleUpdateRequest roleUpdateRequest) {
        RoleResponse roleResponse = roleService.updateRole(roleId, roleUpdateRequest);
        return ResponseUtil.createApiDataResponse(roleResponse, ROLE_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable long roleId) {
        roleService.deleteRole(roleId);
        return ResponseUtil.createApiResponse(ROLE_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}