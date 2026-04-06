package com.washify.api.core.service;

import com.washify.api.core.domain.User;
import com.washify.api.core.dto.RegisterRequest;
import com.washify.api.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        RegisterRequest request = new RegisterRequest(
                "test@mail.com", "pass123", "Ion", "Popescu", "0722", "USER", null
        );

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPass");

        // When
        authService.register(request);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder).encode("pass123");
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        RegisterRequest request = new RegisterRequest("exista@mail.com", "p", "I", "P", "0", "USER", null);
        when(userRepository.existsByEmail("exista@mail.com")).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.register(request));
    }
}