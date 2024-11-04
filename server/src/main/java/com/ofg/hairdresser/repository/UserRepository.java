package com.ofg.hairdresser.repository;

import com.ofg.hairdresser.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.active = true")
    Page<User> findAllActiveUsers(Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId AND u.active = true")
    Optional<User> findActiveByIdWithRoles(Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email AND u.active = true")
    Optional<User> findActiveByEmailWithRoles(@Param("email") String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationToken(String token);

    Optional<User> findByPasswordResetToken(String passwordResetToken);
}
