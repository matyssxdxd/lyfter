package com.lyfter.backend.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

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

    @Override
    public UserInfoResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new UserInfoResponse(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles);
    }

    @Override
    public ResponseCookie generateJwtCookie(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateJwtCookie(userDetails);
    }

    @Override
    public void registerUser(SignupRequest signupRequest) throws Exception {
        if (userRepository.existsByUsername(signupRequest.getUsername())) throw new Exception("Error: Username is already taken");

        if (userRepository.existsByEmail(signupRequest.getEmail())) throw new Exception("Error: Email is already in use");

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public ResponseCookie logoutUser() {
        return jwtUtils.getCleanJwtCookie();
    }
}
