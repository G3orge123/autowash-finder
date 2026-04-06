package com.washify.api.core.service;

import com.washify.api.core.domain.Review;
import com.washify.api.core.domain.User;
import com.washify.api.core.domain.WashStation;
import com.washify.api.core.dto.ReviewRequest;
import com.washify.api.infrastructure.repository.ReviewRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private WashStationRepository washStationRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldAddReviewSuccessfully() {
        // Given
        User user = new User();
        user.setEmail("client@test.com");

        WashStation station = new WashStation();
        station.setId(1L);

        ReviewRequest request = new ReviewRequest(5, "Excelent!", 1L);

        when(washStationRepository.findById(1L)).thenReturn(Optional.of(station));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Review result = reviewService.addReview(user, request);

        // Then
        assertNotNull(result);
        assertEquals(5, result.getRating());
        assertEquals("Excelent!", result.getComment());
        assertEquals(user, result.getUser());
        verify(reviewRepository, times(1)).save(any());
    }
}