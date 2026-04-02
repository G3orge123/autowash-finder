package com.washify.api.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationStatsResponse {
    private Long stationId;
    private String stationName;
    private Double averageRating;
    private Long totalReviews;
    private Double totalRevenue;
    private Long completedBookings;
}