package com.washify.api.core.service;

import com.washify.api.core.domain.Booking;
import com.washify.api.core.domain.BookingStatus;
import com.washify.api.core.domain.ServiceEntity;
import com.washify.api.core.domain.User;
import com.washify.api.core.dto.BookingRequest;
import com.washify.api.infrastructure.repository.BookingRepository;
import com.washify.api.infrastructure.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public Booking createBooking(User user, BookingRequest request) {
        ServiceEntity service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviciul nu există"));

        LocalDateTime start = request.startTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        List<Booking> overlaps = bookingRepository.findOverlappingBookings(
                service.getWashStation().getId(), start, end);

        if (!overlaps.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Intervalul orar este deja ocupat!");
        }

        return bookingRepository.save(Booking.builder()
                .customer(user)
                .service(service)
                .startTime(start)
                .endTime(end)
                .status(BookingStatus.PENDING)
                .totalPrice(service.getPrice())
                .build());
    }
}