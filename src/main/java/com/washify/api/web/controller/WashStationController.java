package com.washify.api.web.controller;

import com.washify.api.core.domain.User;
import com.washify.api.core.dto.WashStationRequest;
import com.washify.api.core.service.WashStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class WashStationController {

    private final WashStationService washStationService;

    @PostMapping
    public ResponseEntity<?> createStation(@RequestBody WashStationRequest request) {
        return ResponseEntity.ok(washStationService.createStation(request));
    }

    @GetMapping
    public ResponseEntity<?> getMyStations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(washStationService.getStationsForUser(user));
    }
}