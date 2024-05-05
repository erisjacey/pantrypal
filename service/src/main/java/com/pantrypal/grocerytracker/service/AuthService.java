package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.auth.AuthLoginRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse logUserIn(AuthLoginRequest request);

    AuthResponse registerUser(AuthRegisterRequest request);
}
