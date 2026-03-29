package com.washify.api.core.dto;

public record RegisterRequest(String email, String password, String firstName, String lastName) {}
