package com.pantrypal.grocerytracker.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private ServletWebRequest servletWebRequest;
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getMethod()).thenReturn("GET");
        servletWebRequest = new ServletWebRequest(servletRequest);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Test handle IllegalArgumentException - returns bad request")
    void handleIllegalArgumentException_returnsBadRequest() {
        // Arrange
        String exceptionMessage = "Invalid argument";
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(exceptionMessage);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleIllegalArgumentException(
                illegalArgumentException, servletWebRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assertNotNull(errorDetails);
        assertEquals(exceptionMessage, errorDetails.getMessage());
    }

    @Test
    @DisplayName("Test handle EntityNotFoundException - returns not found")
    void handleEntityNotFoundException_returnsNotFound() {
        // Arrange
        String exceptionMessage = "Entity not found";
        EntityNotFoundException entityNotFoundException = new EntityNotFoundException(exceptionMessage);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleEntityNotFoundException(
                entityNotFoundException, servletWebRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assertNotNull(errorDetails);
        assertEquals(exceptionMessage, errorDetails.getMessage());
    }
}
