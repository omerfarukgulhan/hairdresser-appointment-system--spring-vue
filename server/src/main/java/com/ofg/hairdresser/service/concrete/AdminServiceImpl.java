package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import com.ofg.hairdresser.service.abstact.AdminService;
import com.ofg.hairdresser.service.abstact.HairdresserService;
import com.ofg.hairdresser.service.abstact.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final HairdresserService hairdresserService;
    private final UserRoleService userRoleService;

    @Autowired
    public AdminServiceImpl(HairdresserService hairdresserService, UserRoleService userRoleService) {
        this.hairdresserService = hairdresserService;
        this.userRoleService = userRoleService;
    }

    @Override
    public HairdresserResponse activateHairdresser(long hairdresserId) {
        Hairdresser hairdresser = hairdresserService.getInactiveHairdresserEntityById(hairdresserId);
        hairdresser.setActive(true);
        User user = hairdresser.getUser();
        userRoleService.assignRoleToUser(user.getId(), "ROLE_HAIRDRESSER");
        return new HairdresserResponse(hairdresser);
    }
}
