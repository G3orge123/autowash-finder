package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByWashStationIdOrderByCreatedAtDesc(Long washStationId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.washStation.id = :stationId")
    Double getAverageRatingForStation(Long stationId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.washStation.id = :stationId")
    Long countReviewsForStation(Long stationId);
}