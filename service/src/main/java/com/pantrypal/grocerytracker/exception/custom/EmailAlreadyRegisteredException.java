package com.pantrypal.grocerytracker.exception.custom;

import com.pantrypal.grocerytracker.constants.Constants;
import org.springframework.security.core.AuthenticationException;

public class EmailAlreadyRegisteredException extends AuthenticationException {
    public EmailAlreadyRegisteredException() {
        super(Constants.ERROR_MESSAGE_EMAIL_REGISTERED);
    }
}
