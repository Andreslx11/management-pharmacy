package com.example.managementpharmacy.shared.exception.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;



// This class is used to structure the error message when an argument is not valid.
// It contains:
// 1. A 'message' field to store a general error message describing the issue (for example,
// "Argument not valid").
// 2. A 'Map' to store specific validation errors for individual fields. The map contains:
//    - The field name as the key (for example, "name", "email").
//    - A detailed description of the error for that field as the value (for example,
//    ""Trade name must be between 3 and 255 characters"", "Email format is invalid").


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArgumentNotValidError {

    private  String message;

    private Map<String, String> error;

}
