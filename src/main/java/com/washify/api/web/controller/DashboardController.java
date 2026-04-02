package com.washify.api.web.controller;

import com.washify.api.core.dto.StationStatsResponse;
import com.washify.api.core.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats/{stationId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<StationStatsResponse> getStats(@PathVariable Long stationId) {
        return ResponseEntity.ok(dashboardService.getStationStats(stationId));
    }
}