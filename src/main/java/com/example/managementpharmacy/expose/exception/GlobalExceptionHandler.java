package com.example.managementpharmacy.expose.exception;


import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleSupplierNotFoundException(DataNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GeneralError> handlerTypeMismatch(MethodArgumentTypeMismatchException ex){
        String message =  String.format("The value '%s' is not valid for the parameter '%s'.",
                ex.getValue(), ex.getName());
        GeneralError errorResponse = new GeneralError(message);
        return ResponseEntity.badRequest().body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidError>  handlerArgumentNotValidError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( error ->
                errors.put(error.getField(), error.getDefaultMessage()));

       ArgumentNotValidError response = new ArgumentNotValidError("Validation errors ", errors);

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GeneralError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Get the root cause of the exception
        Throwable rootCause = ex.getRootCause();

        // If the root cause is null, use a default message
        String errorMessage = (rootCause != null) ? rootCause.getMessage() : "Unknown error occurred.";

        // Create the response object with the error message
        GeneralError errorResponse = new GeneralError("Invalid data: " + errorMessage);

        // Return the response with HTTP status 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



}
