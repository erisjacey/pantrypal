package com.pantrypal.grocerytracker.service;

import com.pantrypal.grocerytracker.dto.auth.AuthLoginRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse logUserIn(AuthLoginRequest request);

    AuthResponse registerUser(AuthRegisterRequest request);

    /**
     * Retrieves the user ID of the currently authenticated user from the SecurityContext.
     * Ensures that the user ID is fetched only from the authenticated context.
     *
     * @return The user ID of the authenticated user.
     * @throws IllegalStateException if no authentication is present or token is invalid.
     */
    public Long getCurrentUserId();
}
