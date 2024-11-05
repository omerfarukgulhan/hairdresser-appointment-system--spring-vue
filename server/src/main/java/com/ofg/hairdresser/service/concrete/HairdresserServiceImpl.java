package com.ofg.hairdresser.service.concrete;

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

@Service
public class HairdresserServiceImpl implements HairdresserService {
    private final HairdresserRepository hairdresserRepository;
    private final UserService userService;

    @Autowired
    public HairdresserServiceImpl(HairdresserRepository hairdresserRepository, UserService userService) {
        this.hairdresserRepository = hairdresserRepository;
        this.userService = userService;
    }

    @Override
    public Page<HairdresserResponse> getAllHairdressers(Pageable pageable) {
        return hairdresserRepository.findAllActiveHairdresser(pageable).map(HairdresserResponse::new);
    }

    @Override
    public HairdresserResponse getHairdresserResponseByUserId(long userId) {
        return hairdresserRepository.findByUserId(userId)
                .map(HairdresserResponse::new)
                .orElseThrow(() -> new NotFoundException(userId));
    }

    @Override
    public Hairdresser getHairdresserEntityByUserId(long userId) {
        return hairdresserRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(userId));
    }

    @Override
    public HairdresserResponse getHairdresserResponseById(long hairdresserId) {
        return hairdresserRepository.findActiveById(hairdresserId)
                .map(HairdresserResponse::new)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
    }

    @Override
    public Hairdresser getHairdresserEntityById(long hairdresserId) {
        return hairdresserRepository.findActiveById(hairdresserId)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
    }

    @Override
    public HairdresserResponse addHairdresser(long userId, HairdresserCreateRequest hairdresserCreateRequest) {
        Hairdresser hairdresser = hairdresserCreateRequest.toHairdresser();
        User user = userService.getUserEntityById(userId);
        hairdresser.setUser(user);
        Hairdresser savedHairdresser = hairdresserRepository.save(hairdresser);
        return new HairdresserResponse(savedHairdresser);
    }

    @Override
    public HairdresserResponse updateHairdresser(long hairdresserId, HairdresserUpdateRequest hairdresserUpdateRequest) {
        Hairdresser existingHairdresser = hairdresserRepository.findById(hairdresserId)
                .orElseThrow(() -> new NotFoundException(hairdresserId));
        updateHairdresserDetails(existingHairdresser, hairdresserUpdateRequest);
        Hairdresser updatedHairdresser = hairdresserRepository.save(existingHairdresser);
        return new HairdresserResponse(updatedHairdresser);
    }

    @Override
    public void deleteHairdresser(long hairdresserId) {
        hairdresserRepository.deleteById(hairdresserId);
    }

    private void updateHairdresserDetails(Hairdresser hairdresser, HairdresserUpdateRequest hairdresserUpdateRequest) {
        hairdresser.setBio(hairdresserUpdateRequest.bio());
        hairdresser.setRating(hairdresserUpdateRequest.rating());
        hairdresser.setYearsOfExperience(hairdresserUpdateRequest.yearsOfExperience());
        hairdresser.setSpecialties(hairdresserUpdateRequest.specialties());
    }
}