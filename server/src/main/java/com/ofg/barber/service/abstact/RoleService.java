package com.ofg.barber.service.abstact;

import com.ofg.barber.model.entity.Role;
import com.ofg.barber.model.request.RoleCreateRequest;
import com.ofg.barber.model.request.RoleUpdateRequest;
import com.ofg.barber.model.response.RoleListResponse;
import com.ofg.barber.model.response.RoleResponse;
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
