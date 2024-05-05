package com.pantrypal.grocerytracker.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRegisterRequest extends AuthRequest {
    private String email;
}
