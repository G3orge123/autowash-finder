package com.washify.api.core.service;

import com.washify.api.core.domain.WashStation;
import com.washify.api.infrastructure.repository.BookingRepository;
import com.washify.api.infrastructure.repository.ReviewRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private WashStationRepository washStationRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void shouldReturnZeroStatsWhenNoDataExists() {

        Long stationId = 1L;
        WashStation station = new WashStation();
        station.setName("Magic Wash");

        when(washStationRepository.findById(stationId)).thenReturn(Optional.of(station));
        when(reviewRepository.getAverageRatingForStation(stationId)).thenReturn(null);
        when(bookingRepository.getRevenueByStatus(any(), any())).thenReturn(null);


        var stats = dashboardService.getStationStats(stationId);

        org.junit.jupiter.api.Assertions.assertEquals(0.0, stats.getAverageRating());
        org.junit.jupiter.api.Assertions.assertEquals(0.0, stats.getTotalRevenue());
        org.junit.jupiter.api.Assertions.assertEquals("Magic Wash", stats.getStationName());
    }
}