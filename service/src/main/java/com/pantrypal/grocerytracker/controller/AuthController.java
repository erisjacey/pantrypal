package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import com.pantrypal.grocerytracker.dto.auth.AuthLoginRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthResponse;
import com.pantrypal.grocerytracker.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.AUTH_API_BASE_PATH)
@Tag(name = "Auth Controller", description = "Endpoints for user authentication")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(Constants.AUTH_API_LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
        AuthResponse response = authService.logUserIn(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(Constants.AUTH_API_REGISTER)
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest request) {
        AuthResponse response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}
