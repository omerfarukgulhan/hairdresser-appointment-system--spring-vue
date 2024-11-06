package com.ofg.hairdresser.service.abstact;

public interface UserRoleService {
    void assignRoleToUser(long userId, String roleName);
}
