package com.pantrypal.grocerytracker.controller;

import com.pantrypal.grocerytracker.constants.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.AUTH_API_BASE_PATH)
@Tag(name = "Auth Controller", description = "Endpoints for user authentication")
public class AuthController {

    @PostMapping(Constants.AUTH_API_LOGIN)
    public ResponseEntity<?> login() {
        // TODO: Implement login controller method
        return null;
    }

    @PostMapping(Constants.AUTH_API_REGISTER)
    public ResponseEntity<?> register() {
        // TODO: Implement register controller method
        return null;
    }
}
