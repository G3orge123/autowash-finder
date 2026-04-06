package com.washify.api.core.service;

import com.washify.api.core.domain.Booking;
import com.washify.api.core.domain.ServiceEntity;
import com.washify.api.core.domain.User;
import com.washify.api.core.domain.WashStation;
import com.washify.api.core.dto.BookingRequest;
import com.washify.api.infrastructure.repository.BookingRepository;
import com.washify.api.infrastructure.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Activează Mockito
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldThrowExceptionWhenBookingOverlaps() {

        LocalDateTime start = LocalDateTime.now();


        ServiceEntity mockService = new ServiceEntity();
        mockService.setDurationMinutes(30);
        mockService.setWashStation(new WashStation());

        BookingRequest request = new BookingRequest(1L, start);


        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));


        when(bookingRepository.findOverlappingBookings(any(), any(), any()))
                .thenReturn(List.of(new Booking()));

        assertThrows(ResponseStatusException.class, () -> {
            bookingService.createBooking(null, request);
        });
    }
    @Test
    void shouldCreateBookingSuccessfully() {
        // 1. PREGĂTIRE (Given)
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        User mockUser = new User();
        mockUser.setEmail("test@washify.com");

        ServiceEntity mockService = new ServiceEntity();
        mockService.setDurationMinutes(45);
        mockService.setPrice(50.0);
        mockService.setWashStation(new WashStation());

        BookingRequest request = new BookingRequest(1L, start);


        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));


        when(bookingRepository.findOverlappingBookings(any(), any(), any()))
                .thenReturn(java.util.Collections.emptyList());


        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Booking result = bookingService.createBooking(mockUser, request);


        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals(mockUser, result.getCustomer());;
        org.junit.jupiter.api.Assertions.assertEquals(50.0, result.getTotalPrice());


        org.mockito.Mockito.verify(bookingRepository, org.mockito.Mockito.times(1)).save(any());
    }
}