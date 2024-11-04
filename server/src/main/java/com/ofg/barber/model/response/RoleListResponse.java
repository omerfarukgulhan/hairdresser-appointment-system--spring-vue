package com.ofg.barber.model.response;

import com.ofg.barber.model.entity.Role;

public record RoleListResponse(Long id, String name) {
    public RoleListResponse(Role role) {
        this(role.getId(), role.getName());
    }
}