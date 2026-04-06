package com.washify.api.core.service;

import com.washify.api.core.domain.User;
import com.washify.api.core.domain.WashStation;
import com.washify.api.core.dto.WashStationRequest;
import com.washify.api.infrastructure.repository.UserRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import com.washify.api.infrastructure.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WashStationServiceTest {

    @Mock private WashStationRepository washStationRepository;
    @Mock private UserRepository userRepository;
    @Mock private ServiceRepository serviceRepository;

    @InjectMocks
    private WashStationService washStationService;

    @Test
    void shouldCreateStationSuccessfully() {
        // Given
        WashStationRequest request = new WashStationRequest(
                "Nume",
                "Adresa",
                44.4,
                26.1,
                1L
        );

        User owner = new User();
        owner.setId(1L);
        owner.setEmail("owner@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(washStationRepository.save(any(WashStation.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        WashStation result = washStationService.createStation(request);

        // Then
        assertNotNull(result);
        assertEquals("Nume", result.getName());
        assertEquals(owner, result.getOwner());
        assertNotNull(result.getLocation());
        verify(washStationRepository).save(any());
    }

    @Test
    void shouldDeleteStation() {
        // Given
        when(washStationRepository.existsById(1L)).thenReturn(true);

        // When
        washStationService.deleteStation(1L);

        // Then
        verify(washStationRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentStation() {
        when(washStationRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> washStationService.deleteStation(1L));
    }
}