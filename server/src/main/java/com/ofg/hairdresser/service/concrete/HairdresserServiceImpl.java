package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.authentication.UnauthorizedException;
import com.ofg.hairdresser.exception.general.NotFoundException;
import com.ofg.hairdresser.model.entity.Hairdresser;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.HairdresserCreateRequest;
import com.ofg.hairdresser.model.request.HairdresserUpdateRequest;
import com.ofg.hairdresser.model.response.HairdresserResponse;
import com.ofg.hairdresser.repository.HairdresserRepository;
import com.ofg.hairdresser.service.abstact.HairdresserService;
import com.ofg.hairdresser.service.abstact.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HairdresserServiceImpl implements HairdresserService {
    private final HairdresserRepository hairdresserRepository;
    private final UserService userService;

    @Autowired
    public HairdresserServiceImpl(HairdresserRepository hairdresserRepository,
                                  UserService userService) {
        this.hairdresserRepository = hairdresserRepository;
        this.userService = userService;
    }

    @Override
    public Page<HairdresserResponse> getAllHairdressers(Pageable pageable) {
        return hairdresserRepository.findAllActiveHairdresser(pageable).map(HairdresserResponse::new);
    }

    @Override
    public Hairdresser getHairdresserEntityByUserId(long userId) {
        return hairdresserRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(userId));
    }

    @Override
    public HairdresserResponse getHairdresserResponseById(long hairdresserId) {
        return findActiveHairdresserById(hairdresserId)
                .map(HairdresserResponse::new)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
    }

    @Override
    public Hairdresser getHairdresserEntityById(long hairdresserId) {
        return findActiveHairdresserById(hairdresserId)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
    }

    @Override
    public Hairdresser getInactiveHairdresserEntityById(long hairdresserId) {
        return hairdresserRepository.findInactiveById(hairdresserId)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
    }

    @Override
    public HairdresserResponse addHairdresser(long userId, HairdresserCreateRequest hairdresserCreateRequest) {
        User user = userService.getUserEntityById(userId);
        Hairdresser hairdresser = hairdresserCreateRequest.toHairdresser();
        hairdresser.setUser(user);
        Hairdresser savedHairdresser = hairdresserRepository.save(hairdresser);
        return new HairdresserResponse(savedHairdresser);
    }

    @Override
    public HairdresserResponse updateHairdresser(long userId, long hairdresserId, HairdresserUpdateRequest hairdresserUpdateRequest) {
        Hairdresser existingHairdresser = getAndValidateHairdresserOwnership(hairdresserId, userId);
        updateHairdresserDetails(existingHairdresser, hairdresserUpdateRequest);
        Hairdresser updatedHairdresser = hairdresserRepository.save(existingHairdresser);
        return new HairdresserResponse(updatedHairdresser);
    }

    @Override
    public void deleteHairdresser(long userId, long hairdresserId) {
        getAndValidateHairdresserOwnership(hairdresserId, userId);
        hairdresserRepository.deleteById(hairdresserId);
    }

    private void updateHairdresserDetails(Hairdresser hairdresser, HairdresserUpdateRequest hairdresserUpdateRequest) {
        hairdresser.setBio(hairdresserUpdateRequest.bio());
        hairdresser.setYearsOfExperience(hairdresserUpdateRequest.yearsOfExperience());
        hairdresser.setSpecialties(hairdresserUpdateRequest.specialties());
    }

    private Hairdresser getAndValidateHairdresserOwnership(long hairdresserId, long userId) {
        Hairdresser hairdresser = hairdresserRepository.findById(hairdresserId)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
        if (hairdresser.getUser().getId() != userId) {
            throw new UnauthorizedException();
        }
        return hairdresser;
    }

    private Optional<Hairdresser> findActiveHairdresserById(long hairdresserId) {
        return hairdresserRepository.findActiveById(hairdresserId);
    }
}