package com.washify.api.web.controller;

import com.washify.api.core.domain.Booking;
import com.washify.api.core.domain.User;
import com.washify.api.core.dto.BookingRequest;
import com.washify.api.core.dto.ServiceRequest;
import com.washify.api.core.dto.WashStationRequest;
import com.washify.api.core.service.BookingService;
import com.washify.api.core.service.WashStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> create(@AuthenticationPrincipal User user, @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(user, request));
    }
}