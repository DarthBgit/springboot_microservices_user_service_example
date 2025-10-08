package com.app.userservice.repository;

import com.app.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndIdNot(String username, Long currentUserId);
    boolean existsByEmailAndIdNot(String email, Long currentUserId);
    Optional<User> findByUsername(String username);
}
