package com.washify.api.web.controller;

import com.washify.api.core.domain.*;
import com.washify.api.core.dto.*;
import com.washify.api.infrastructure.repository.*;
import com.washify.api.infrastructure.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authManager, UserRepository userRepo,
                          PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authManager;
        this.userRepository = userRepo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        String jwt = jwtUtils.generateJwtToken(loginRequest.email());
        return ResponseEntity.ok(new AuthResponse(jwt, loginRequest.email()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setPassword(encoder.encode(signUpRequest.password())); // BCrypt Encoding

        // UserProfile logic
        UserProfile profile = new UserProfile();
        profile.setFirstName(signUpRequest.firstName());
        profile.setLastName(signUpRequest.lastName());
        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}