package com.example.managementpharmacy.shared.constant;



/**
 * This class holds the HTTP status codes as constant values.
 *
 * These status codes are used to standardize the responses sent by the API.
 * For example:
 * - OK (200) indicates that the request was successful.
 * - CREATED (201) indicates that a resource has been successfully created.
 * - BAD_REQUEST (400) indicates that the request was malformed or invalid.
 * - NOT_FOUND (404) indicates that the requested resource could not be found.

 */

public class StatusCode {

    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";

    /*
    This constructor is to prevent someone from accidentally  trying  to install
    this class
    */
    private StatusCode() {
    }

}
