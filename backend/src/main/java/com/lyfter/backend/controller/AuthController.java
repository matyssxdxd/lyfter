package com.lyfter.backend.controller;

import com.lyfter.backend.payload.request.LoginRequest;
import com.lyfter.backend.payload.request.SignupRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.payload.response.UserInfoResponse;
import com.lyfter.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.generateJwtCookie(loginRequest).toString())
                .body(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logoutUser() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.logoutUser().toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
