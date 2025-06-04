package com.example.fortifyapp.controller;

import com.example.fortifyapp.security.JwtService;
import com.example.fortifyapp.entity.User;
import com.example.fortifyapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        return userRepository.findByUsername(body.get("username"))
                .filter(user -> passwordEncoder.matches(body.get("password"), user.getPassword()))
                .map(user -> ResponseEntity.ok(Map.of("token", jwtService.generateToken(user.getUsername()))))
                .orElse(ResponseEntity.status(401).build());
    }
}
