package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // used for  CustomUserDetailsService and AuthController
    Optional<User> findByEmail(String email);

    //method used for validating the mail
    boolean existsByEmail(String email);

    @Query(value = "SELECT r.name FROM roles r JOIN user_roles ur ON r.id = ur.role_id JOIN users u ON u.id = ur.user_id WHERE u.email = :email", nativeQuery = true)
    List<String> findRawRolesByEmail(@Param("email") String email);
}