package com.foodsub.backend.repository;

import com.foodsub.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByResetToken(String resetToken);
}
