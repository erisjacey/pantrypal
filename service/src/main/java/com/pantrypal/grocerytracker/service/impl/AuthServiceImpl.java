package com.pantrypal.grocerytracker.service.impl;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.auth.AuthLoginRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthResponse;
import com.pantrypal.grocerytracker.exception.custom.EmailAlreadyRegisteredException;
import com.pantrypal.grocerytracker.exception.custom.UsernameAlreadyExistsException;
import com.pantrypal.grocerytracker.mapper.UserMapper;
import com.pantrypal.grocerytracker.model.AuthUser;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.repository.UserRepository;
import com.pantrypal.grocerytracker.service.AuthService;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            JwtEncoder jwtEncoder,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        // Check if the username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Map the request and encoded password to the entity to save
        User user = userMapper.mapToEntity(request, encodedPassword);

        // Save the user to the database
        userRepository.save(user);

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
            throw new IllegalStateException("No valid authentication found for the current user.");
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
