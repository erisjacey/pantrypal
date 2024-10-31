package com.pantrypal.grocerytracker.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a registration request extending {@link AuthRequest}.
 * Includes an email field for registration purposes.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRegisterRequest extends AuthRequest {
    /**
     * The email address for registration.
     */
    @NotNull
    private String email;
}
