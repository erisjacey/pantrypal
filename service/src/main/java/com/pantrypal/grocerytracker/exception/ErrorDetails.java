package com.pantrypal.grocerytracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents details of an error, including timestamp, message, and path.
 */
@AllArgsConstructor
@Setter
@Getter
public class ErrorDetails {
    /**
     * The timestamp when the error occurred.
     */
    private Date timestamp;

    /**
     * The error message.
     */
    private String message;

    /**
     * The path of the request where the error occurred.
     */
    private String path;

    /**
     * The API method (GET, POST, etc.) associated with the request.
     */
    private String method;
}
