package com.lyfter.backend.controller;

import com.lyfter.backend.model.Role;
import com.lyfter.backend.model.RoleEnum;
import com.lyfter.backend.model.User;
import com.lyfter.backend.payload.request.LoginRequest;
import com.lyfter.backend.payload.request.SignupRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.payload.response.UserInfoResponse;
import com.lyfter.backend.repo.RoleRepository;
import com.lyfter.backend.repo.UserRepository;
import com.lyfter.backend.security.jwt.JwtUtils;
import com.lyfter.backend.security.service.UserDetailsImpl;
import com.lyfter.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.generateJwtCookie(loginRequest).toString())
                .body(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.logoutUser().toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
