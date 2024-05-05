package com.pantrypal.grocerytracker.dto.auth;

import lombok.Data;

@Data
public abstract class AuthRequest {
    protected String username;
    protected String password;
}
