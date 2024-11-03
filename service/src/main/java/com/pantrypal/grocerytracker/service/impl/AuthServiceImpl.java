package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.auth.AuthLoginRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthResponse;
import com.pantrypal.grocerytracker.model.AuthUser;
import com.pantrypal.grocerytracker.service.AuthService;
import com.pantrypal.grocerytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserService userService,
            JwtEncoder jwtEncoder,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse logUserIn(AuthLoginRequest request) {
        Authentication authentication = authenticateUser(request);
        String token = generateToken(authentication);
        return new AuthResponse(Constants.SUCCESS_MESSAGE_USER_LOGGED_IN, token);
    }

    @Override
    public AuthResponse registerUser(AuthRegisterRequest request) {
        // Encode the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Register user with user service
        userService.registerUser(request, encodedPassword);

        // Authenticate user and generate token
        Authentication authentication = authenticateUser(request);
        String token = generateToken(authentication);

        // Return a success response
        return new AuthResponse(Constants.SUCCESS_MESSAGE_USER_REGISTERED, token);
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException(Constants.ERROR_MESSAGE_INVALID_AUTHENTICATION);
        }

        return jwt.getClaim(Constants.JWT_KEY_USER_ID);
    }

    private Authentication authenticateUser(AuthRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim(Constants.JWT_KEY_SCOPE, scope)
                .claim(Constants.JWT_KEY_USER_ID, ((AuthUser) authentication.getPrincipal()).getId())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
