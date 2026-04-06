package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.WashStation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WashStationRepository extends JpaRepository<WashStation, Long> {
    List<WashStation> findByOwnerId(Long ownerId);
}