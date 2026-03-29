package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.Role;
import com.washify.api.core.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}