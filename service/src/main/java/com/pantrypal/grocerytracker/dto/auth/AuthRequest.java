package com.pantrypal.grocerytracker.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Abstract base class representing an authentication request.
 */
@Data
public abstract class AuthRequest {
    /**
     * The username for authentication.
     */
    @NotNull
    protected String username;

    /**
     * The password for authentication.
     */
    @NotNull
    protected String password;
}
