package com.washify.api.infrastructure.repository;

import com.washify.api.core.domain.Booking;
import com.washify.api.core.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    @Query("SELECT b FROM Booking b WHERE b.service.washStation.id = :stationId " +
            "AND ((b.startTime < :end AND b.endTime > :start))")
    List<Booking> findOverlappingBookings(
            @Param("stationId") Long stationId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);


    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.service.washStation.id = :stationId AND b.status = :status")
    Double getRevenueByStatus(@Param("stationId") Long stationId, @Param("status") BookingStatus status);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.service.washStation.id = :stationId AND b.status = :status")
    Long countByStatus(@Param("stationId") Long stationId, @Param("status") BookingStatus status);
}