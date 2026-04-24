package com.hospital.auth.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.auth.dto.AuthRequest;
import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.entity.User;
import com.hospital.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthService authService;
    
    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody User user) {
        logger.info("Received request to register user: {}", user.getEmail());
        AuthResponse response = authService.register(user);
        if (response.getMessage().contains("Email already exists")) {
            logger.warn("Registration failed: {}", response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        logger.info("Registration successful for: {}", user.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        logger.info("Received login request for: {}", request.getEmail());
        try {
            AuthResponse response = authService.login(request);
            logger.info("Login successful for: {}", request.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Login failed for: {} - {}", request.getEmail(), e.getMessage());
            return new ResponseEntity<>(new AuthResponse(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
    // Validate Token Endpoint
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        logger.debug("Received token validation request");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean isValid = authService.validateToken(token);
        logger.debug("Token validation result: {}", isValid);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
    
    // Get Email from Token
    @PostMapping("/get-email")
    public ResponseEntity<String> getEmailFromToken(@RequestHeader("Authorization") String token) {
        logger.debug("Received request to extract email from token");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = authService.getEmailFromToken(token);
        logger.debug("Extracted email: {}", email);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }
}