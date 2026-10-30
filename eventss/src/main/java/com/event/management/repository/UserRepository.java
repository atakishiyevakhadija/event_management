package com.event.management.repository;

import com.event.management.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String fullName);
    Optional<User> findByEmail(String email);
    Optional<User> existsByEmail(String email);
}