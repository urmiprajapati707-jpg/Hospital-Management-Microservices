package com.hospital.auth.service;

import com.hospital.auth.dto.AuthRequest;
import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.entity.User;
import com.hospital.auth.repository.UserRepository;
import com.hospital.auth.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private User user;
    private AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("PATIENT");

        authRequest = new AuthRequest();
        authRequest.setEmail("test@test.com");
        authRequest.setPassword("123456");
    }

    // Test 1: Register Success
    @Test
    void testRegister_Success() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        AuthResponse response = authService.register(user);

        assertNotNull(response);
        assertEquals("User registered successfully!", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test 2: Register with Existing Email
    @Test
    void testRegister_EmailAlreadyExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        AuthResponse response = authService.register(user);

        assertNotNull(response);
        assertEquals("Email already exists!", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // Test 3: Login Success
    @Test
    void testLogin_Success() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getEmail(), user.getRole())).thenReturn("test.jwt.token");

        AuthResponse response = authService.login(authRequest);

        assertNotNull(response);
        assertEquals("Login successful!", response.getMessage());
        assertNotNull(response.getToken());
    }

    // Test 4: Login with Wrong Email
    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        AuthRequest wrongRequest = new AuthRequest();
        wrongRequest.setEmail("wrong@test.com");
        wrongRequest.setPassword("123456");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(wrongRequest);
        });

        assertEquals("User not found!", exception.getMessage());
    }

    // Test 5: Login with Wrong Password
    @Test
    void testLogin_WrongPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        AuthRequest wrongRequest = new AuthRequest();
        wrongRequest.setEmail("test@test.com");
        wrongRequest.setPassword("wrongpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(wrongRequest);
        });

        assertEquals("Invalid password!", exception.getMessage());
    }

    // Test 6: Validate Token - Valid
    @Test
    void testValidateToken_Valid() {
        String token = "valid.token";
        when(jwtUtil.validateToken(token)).thenReturn(true);

        boolean isValid = authService.validateToken(token);

        assertTrue(isValid);
    }

    // Test 7: Validate Token - Invalid
    @Test
    void testValidateToken_Invalid() {
        String token = "invalid.token";
        when(jwtUtil.validateToken(token)).thenReturn(false);

        boolean isValid = authService.validateToken(token);

        assertFalse(isValid);
    }
}