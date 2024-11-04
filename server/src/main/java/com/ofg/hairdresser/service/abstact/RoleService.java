package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.entity.Role;
import com.ofg.hairdresser.model.request.RoleCreateRequest;
import com.ofg.hairdresser.model.request.RoleUpdateRequest;
import com.ofg.hairdresser.model.response.RoleListResponse;
import com.ofg.hairdresser.model.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleListResponse> getAllRoles(Pageable pageable);

    RoleResponse getRoleById(long roleId);

    Role getRoleByName(String name);

    RoleResponse addRole(RoleCreateRequest roleCreateRequest);

    RoleResponse updateRole(long roleId, RoleUpdateRequest roleUpdateRequest);

    void deleteRole(long roleId);
}
