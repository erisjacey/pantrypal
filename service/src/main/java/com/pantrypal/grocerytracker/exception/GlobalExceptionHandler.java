package com.pantrypal.grocerytracker.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
