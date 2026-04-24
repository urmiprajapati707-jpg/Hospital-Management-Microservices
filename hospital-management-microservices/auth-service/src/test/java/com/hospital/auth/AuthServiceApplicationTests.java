package com.hospital.auth;

import com.hospital.auth.dto.AuthRequest;
import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.entity.User;
import com.hospital.auth.repository.UserRepository;
import com.hospital.auth.service.AuthService;
import com.hospital.auth.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceApplicationTests {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthService authService;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private User testUser;
    private AuthRequest authRequest;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("123456"));
        testUser.setRole("PATIENT");
        
        authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("123456");
    }
    
    // Test 1: Register Success
    @Test
    void testRegisterSuccess() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        AuthResponse response = authService.register(testUser);
        
        assertNotNull(response);
        assertEquals("User registered successfully!", response.getMessage());
    }
    
    // Test 2: Register with Existing Email
    @Test
    void testRegisterWithExistingEmail() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);
        
        AuthResponse response = authService.register(testUser);
        
        assertNotNull(response);
        assertEquals("Email already exists!", response.getMessage());
    }
    
    // Test 3: Login Success
    @Test
    void testLoginSuccess() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(jwtUtil.generateToken(testUser.getEmail(), testUser.getRole())).thenReturn("test.jwt.token");
        
        AuthResponse response = authService.login(authRequest);
        
        assertNotNull(response);
        assertEquals("Login successful!", response.getMessage());
        assertNotNull(response.getToken());
    }
    
    // Test 4: Login with Wrong Email
    @Test
    void testLoginWithWrongEmail() {
        when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());
        
        AuthRequest wrongRequest = new AuthRequest();
        wrongRequest.setEmail("wrong@example.com");
        wrongRequest.setPassword("123456");
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(wrongRequest);
        });
        
        assertEquals("User not found!", exception.getMessage());
    }
    
    // Test 5: Login with Wrong Password
    @Test
    void testLoginWithWrongPassword() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        
        AuthRequest wrongRequest = new AuthRequest();
        wrongRequest.setEmail("test@example.com");
        wrongRequest.setPassword("wrongpassword");
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(wrongRequest);
        });
        
        assertEquals("Invalid password!", exception.getMessage());
    }
    
    // Test 6: Validate Token Success
    @Test
    void testValidateTokenSuccess() {
        String token = "valid.jwt.token";
        when(jwtUtil.validateToken(token)).thenReturn(true);
        
        boolean isValid = authService.validateToken(token);
        
        assertTrue(isValid);
    }
    
    // Test 7: Validate Token Failure
    @Test
    void testValidateTokenFailure() {
        String token = "invalid.jwt.token";
        when(jwtUtil.validateToken(token)).thenReturn(false);
        
        boolean isValid = authService.validateToken(token);
        
        assertFalse(isValid);
    }
}