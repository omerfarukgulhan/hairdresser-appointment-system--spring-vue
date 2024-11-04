package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.model.entity.Role;

public record RoleListResponse(Long id, String name) {
    public RoleListResponse(Role role) {
        this(role.getId(), role.getName());
    }
}