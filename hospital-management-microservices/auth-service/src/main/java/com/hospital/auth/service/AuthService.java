package com.hospital.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.hospital.auth.dto.AuthRequest;
import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.entity.User;
import com.hospital.auth.repository.UserRepository;
import com.hospital.auth.utils.JwtUtil;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Register User
    public AuthResponse register(User user) {
        logger.info("Register attempt for email: {}", user.getEmail());
        
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Registration failed - Email already exists: {}", user.getEmail());
            return new AuthResponse(null, "Email already exists!");
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save user
        userRepository.save(user);
        logger.info("User registered successfully with email: {}", user.getEmail());
        
        return new AuthResponse(null, "User registered successfully!");
    }
    
    // Login User
    public AuthResponse login(AuthRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("Login failed - User not found: {}", request.getEmail());
                    return new RuntimeException("User not found!");
                });
        
        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login failed - Invalid password for email: {}", request.getEmail());
            throw new RuntimeException("Invalid password!");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        logger.info("Login successful for email: {}", request.getEmail());
        
        return new AuthResponse(token, "Login successful!");
    }
    
    // Google Login
    public AuthResponse googleLogin(String email, String name) {
        logger.info("Google login attempt for email: {}", email);
        
        // Check if user exists
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            // Create new user
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("GOOGLE_AUTH_" + System.currentTimeMillis()));
            user.setRole("PATIENT");
            userRepository.save(user);
            logger.info("New user created via Google: {}", email);
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        logger.info("Google login successful for: {}", email);
        
        return new AuthResponse(token, "Google login successful!");
    }
    
    // Validate Token
    public boolean validateToken(String token) {
        logger.debug("Validating token");
        boolean isValid = jwtUtil.validateToken(token);
        logger.debug("Token validation result: {}", isValid);
        return isValid;
    }
    
    // Get Email from Token
    public String getEmailFromToken(String token) {
        logger.debug("Extracting email from token");
        String email = jwtUtil.extractEmail(token);
        logger.debug("Extracted email: {}", email);
        return email;
    }
}