package com.example.managementpharmacy.expose.exception;


import com.example.managementpharmacy.shared.exception.AuthenticationUserException;
import com.example.managementpharmacy.shared.exception.BusinessRuleViolationException;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<GeneralError> handleSupplierNotFoundException(DataNotFoundException exception) {
       logger.error("Data not found exception occurred: {}", exception.getMessage());
        return getGeneralErrorResponseEntity(exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GeneralError> handlerTypeMismatch(MethodArgumentTypeMismatchException exception){
        String message =  String.format("The value '%s' is not valid for the parameter '%s'.",
                exception.getValue(), exception.getName());
        logger.warn("Type mismatch  exception occurred: {}", message);
        GeneralError errorResponse = new GeneralError(message);
        return ResponseEntity.badRequest().body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidError>  handlerArgumentNotValidError(MethodArgumentNotValidException exception){
        logger.info("Validation occurred exception");
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach( error ->
                errors.put(error.getField(), error.getDefaultMessage()));
       ArgumentNotValidError response = new ArgumentNotValidError("Validation errors ", errors);
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GeneralError> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

        logger.error("Data integrity violation exception occurred: {}", exception.getMessage());

        // Get the root cause of the exception
        Throwable rootCause = exception.getRootCause();

        // If the root cause is null, use a default message
        String errorMessage = (rootCause != null) ? rootCause.getMessage() : "Unknown error occurred.";

        // Create the response object with the error message
        GeneralError errorResponse = new GeneralError("Invalid data: " + errorMessage);

        // Return the response with HTTP status 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<GeneralError>  handleUserAlreadyExists(BusinessRuleViolationException exception){
        logger.warn("Business rule violation: {}", exception.getMessage());
        return getGeneralErrorResponseEntity(exception);
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public ResponseEntity<GeneralError> handleAuthenticationUserException(AuthenticationUserException exception){
        logger.warn("Authentication user exception occurred: {}", exception.getMessage());
        return getGeneralErrorResponseEntity(exception);
    }

    private <T extends Exception>  ResponseEntity<GeneralError> getGeneralErrorResponseEntity(T exception) {
        GeneralError errorResponse = new GeneralError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

}
