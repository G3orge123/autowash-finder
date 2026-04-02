package com.washify.api.web.controller;

import com.washify.api.core.domain.Review;
import com.washify.api.core.domain.User;
import com.washify.api.core.dto.ReviewRequest;
import com.washify.api.core.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final com.washify.api.infrastructure.repository.UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(
            java.security.Principal principal,
            @RequestBody ReviewRequest request) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        return ResponseEntity.ok(reviewService.addReview(user, request));
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<Review>> getReviewsByStation(@PathVariable Long stationId) {
        // Returnează lista de review-uri pentru stația respectivă
        return ResponseEntity.ok(reviewService.getReviewsForStation(stationId));
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<List<Review>> getMyReviews(java.security.Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(reviewService.getReviewsByUser(user.getId()));
    }
}