package com.washify.api.core.service;

import com.washify.api.core.domain.Review;
import com.washify.api.core.domain.User;
import com.washify.api.core.domain.WashStation;
import com.washify.api.core.dto.ReviewRequest;
import com.washify.api.infrastructure.repository.ReviewRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final WashStationRepository washStationRepository;

    @Transactional
    public Review addReview(User user, ReviewRequest request) {
        WashStation station = washStationRepository.findById(request.washStationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stația nu există"));

        Review review = Review.builder()
                .rating(request.rating())
                .comment(request.comment())
                .user(user)
                .washStation(station)
                .build();

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForStation(Long stationId) {
        return reviewRepository.findByWashStationIdOrderByCreatedAtDesc(stationId);
    }

    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}