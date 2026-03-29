package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // used for  CustomUserDetailsService and AuthController
    Optional<User> findByEmail(String email);

    //method used for validating the mail
    boolean existsByEmail(String email);
}