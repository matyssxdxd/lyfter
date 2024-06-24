package com.lyfter.backend.service;

import com.lyfter.backend.payload.request.LoginRequest;
import com.lyfter.backend.payload.request.SignupRequest;
import com.lyfter.backend.payload.response.UserInfoResponse;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    UserInfoResponse authenticateUser(LoginRequest loginRequest);

    ResponseCookie generateJwtCookie(LoginRequest loginRequest);

    void registerUser(SignupRequest signupRequest) throws Exception;

    ResponseCookie logoutUser();
}
