package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.service.washStation.id = :stationId " +
            "AND b.status != 'CANCELLED' " +
            "AND ((b.startTime < :end AND b.endTime > :start))")
    List<Booking> findOverlappingBookings(
            @Param("stationId") Long stationId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<Booking> findByCustomerId(Long customerId);
}