package com.pantrypal.grocerytracker.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a login request extending {@link AuthRequest}.
 * Inherits username and password fields from {@link AuthRequest}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthLoginRequest extends AuthRequest {
}
