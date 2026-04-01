package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {


    List<ServiceEntity> findByWashStationId(Long washStationId);
}