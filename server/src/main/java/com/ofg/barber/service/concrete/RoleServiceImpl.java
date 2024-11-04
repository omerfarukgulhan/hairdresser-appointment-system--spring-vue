package com.ofg.barber.service.concrete;

import com.ofg.barber.exception.general.NotFoundException;
import com.ofg.barber.model.entity.Role;
import com.ofg.barber.model.request.RoleCreateRequest;
import com.ofg.barber.model.request.RoleUpdateRequest;
import com.ofg.barber.model.response.RoleListResponse;
import com.ofg.barber.model.response.RoleResponse;
import com.ofg.barber.repository.RoleRepository;
import com.ofg.barber.service.abstact.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<RoleListResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleListResponse::new);
    }

    @Override
    public RoleResponse getRoleById(long roleId) {
        return roleRepository.findById(roleId)
                .map(RoleResponse::new)
                .orElseThrow(() -> new NotFoundException(roleId));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(name));
    }

    @Override
    public RoleResponse addRole(RoleCreateRequest roleCreateRequest) {
        Role role = new Role();
        role.setName(roleCreateRequest.name());
        Role savedRole = roleRepository.save(role);
        return new RoleResponse(savedRole);
    }

    @Override
    public RoleResponse updateRole(long roleId, RoleUpdateRequest roleUpdateRequest) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));

        role.setName(roleUpdateRequest.name());
        Role updatedRole = roleRepository.save(role);
        return new RoleResponse(updatedRole);
    }

    @Override
    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));
        roleRepository.delete(role);
    }
}
