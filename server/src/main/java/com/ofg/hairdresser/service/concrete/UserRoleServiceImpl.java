package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.model.entity.Role;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.UserUpdateRequest;
import com.ofg.hairdresser.service.abstact.RoleService;
import com.ofg.hairdresser.service.abstact.UserRoleService;
import com.ofg.hairdresser.service.abstact.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final RoleService roleService;
    private final UserService userService;

    public UserRoleServiceImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void assignRoleToUser(long userId, String roleName) {
        Role role = roleService.getRoleByName(roleName);
        User user = userService.getUserEntityById(userId);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(user.getFirstName(), user.getLastName(),user.getPhoneNumber());
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userService.updateUser(user.getId(), userUpdateRequest, null);
        }
    }
}
