package com.pantrypal.grocerytracker.exception.custom;

import com.pantrypal.grocerytracker.constants.Constants;
import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExistsException extends AuthenticationException {
    public UsernameAlreadyExistsException() {
        super(Constants.ERROR_MESSAGE_USERNAME_TAKEN);
    }
}
