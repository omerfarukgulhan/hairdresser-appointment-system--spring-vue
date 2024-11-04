package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Role;

public record RoleResponse(Long id, String name) {
    public RoleResponse(Role role) {
        this(role.getId(), role.getName());
    }
}
