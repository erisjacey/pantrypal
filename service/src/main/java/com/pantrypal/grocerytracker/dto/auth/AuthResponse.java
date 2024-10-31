package com.pantrypal.grocerytracker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a response object for authentication operations.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    /**
     * A message describing the authentication status.
     */
    private String message;

    /**
     * A token generated upon successful authentication.
     */
    private String token;
}
