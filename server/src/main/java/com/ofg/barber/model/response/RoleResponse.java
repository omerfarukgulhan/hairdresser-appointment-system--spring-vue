package com.ofg.barber.model.response;

import com.ofg.barber.model.entity.Role;

public record RoleResponse(Long id, String name) {
    public RoleResponse(Role role) {
        this(role.getId(), role.getName());
    }
}
