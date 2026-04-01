package com.washify.api.web.controller;

import com.washify.api.core.domain.User;
import com.washify.api.core.dto.ServiceRequest;
import com.washify.api.core.dto.WashStationRequest;
import com.washify.api.core.service.WashStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class WashStationController {

    private final WashStationService washStationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createStation(@RequestBody WashStationRequest request) {
        return ResponseEntity.ok(washStationService.createStation(request));
    }

    @GetMapping
    public ResponseEntity<?> getMyStations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(washStationService.getStationsForUser(user));
    }


    @PostMapping("/{stationId}/services")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<?> addServiceToStation(
            @PathVariable Long stationId,
            @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(washStationService.addServiceToStation(stationId, request));
    }

    @GetMapping("/{stationId}/services")
    public ResponseEntity<?> getStationServices(@PathVariable Long stationId) {
        return ResponseEntity.ok(washStationService.getServicesByStation(stationId));
    }
}