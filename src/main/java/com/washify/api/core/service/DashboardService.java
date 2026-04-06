package com.washify.api.core.service;

import com.washify.api.core.domain.BookingStatus;
import com.washify.api.core.dto.StationStatsResponse;
import com.washify.api.core.domain.WashStation;
import com.washify.api.infrastructure.repository.BookingRepository;
import com.washify.api.infrastructure.repository.ReviewRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final WashStationRepository washStationRepository;

    public StationStatsResponse getStationStats(Long stationId) {
        WashStation station = washStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Stația nu există"));

        Double avgRating = reviewRepository.getAverageRatingForStation(stationId);
        Long totalReviews = reviewRepository.countReviewsForStation(stationId);


        Double confirmedRevenue = bookingRepository.getRevenueByStatus(stationId, BookingStatus.COMPLETED);
        Long completedCount = bookingRepository.countByStatus(stationId, BookingStatus.COMPLETED);

        return StationStatsResponse.builder()
                .stationId(stationId)
                .stationName(station.getName())
                .averageRating(avgRating != null ? avgRating : 0.0)
                .totalReviews(totalReviews != null ? totalReviews : 0L)
                .totalRevenue(confirmedRevenue != null ? confirmedRevenue : 0.0)
                .completedBookings(completedCount != null ? completedCount : 0L)
                .build();
    }
}