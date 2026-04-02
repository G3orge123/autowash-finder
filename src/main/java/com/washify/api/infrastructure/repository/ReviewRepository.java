package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByWashStationIdOrderByCreatedAtDesc(Long washStationId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
}