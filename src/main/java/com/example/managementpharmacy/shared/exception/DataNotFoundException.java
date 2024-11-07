package com.example.managementpharmacy.shared.exception;



// Custom exception to be thrown when the requested data is not found.
// It allows providing a specific message to indicate that the data was not found.

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
