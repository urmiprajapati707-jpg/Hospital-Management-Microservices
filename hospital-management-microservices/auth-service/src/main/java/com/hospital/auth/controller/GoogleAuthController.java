package com.hospital.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.hospital.auth.dto.AuthResponse;
import com.hospital.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class GoogleAuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/google-success")
    public AuthResponse googleSuccess(OAuth2AuthenticationToken token) {
        System.out.println("=== Google Callback Received ===");
        
        if (token == null) {
            System.out.println("Token is NULL");
            return new AuthResponse(null, "Google login failed");
        }
        
        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");
        
        System.out.println("Email: " + email);
        System.out.println("Name: " + name);
        
        return authService.googleLogin(email, name);
    }
}