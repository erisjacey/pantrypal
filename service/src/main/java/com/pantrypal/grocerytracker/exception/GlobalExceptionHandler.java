package com.pantrypal.grocerytracker.exception;

import com.pantrypal.grocerytracker.exception.custom.EmailAlreadyRegisteredException;
import com.pantrypal.grocerytracker.exception.custom.UsernameAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * GlobalExceptionHandler handles exceptions globally for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<Object> handleInvalidBearerTokenException(
            InvalidBearerTokenException ex, WebRequest request
    ) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedExceptionException(AccessDeniedException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException ex, WebRequest request
    ) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEmailAlreadyRegisteredException(
            EmailAlreadyRegisteredException ex, WebRequest request
    ) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalArgumentException} and returns a {@link ResponseEntity} with error details.
     *
     * @param ex      The IllegalArgumentException thrown.
     * @param request The WebRequest object representing the request.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request
    ) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link EntityNotFoundException} and returns a {@link ResponseEntity} with error details.
     *
     * @param ex      The EntityNotFoundException thrown.
     * @param request The WebRequest object representing the request.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(ex, request);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Constructs a {@link ErrorDetails} object with timestamp, message, and path.
     *
     * @param ex      The Exception thrown.
     * @param request The WebRequest object representing the request.
     * @return ErrorDetails object.
     */
    private ErrorDetails getErrorDetails(Exception ex, WebRequest request) {
        return new ErrorDetails(
                new Date(),
                ex.getMessage(),
                getRequestPath(request),
                getHttpMethod(request)
        );
    }

    /**
     * Retrieves the request path from the {@link WebRequest}.
     *
     * @param request The WebRequest object representing the request.
     * @return The request path.
     */
    private String getRequestPath(WebRequest request) {
        ServletWebRequest servletRequest = (ServletWebRequest) request;
        return servletRequest.getRequest().getRequestURI();
    }

    /**
     * Retrieves the HTTP method of the request.
     *
     * @param request The WebRequest object representing the request.
     * @return The HTTP method of the request.
     */
    private String getHttpMethod(WebRequest request) {
        ServletWebRequest servletRequest = (ServletWebRequest) request;
        return servletRequest.getHttpMethod().toString();
    }
}
