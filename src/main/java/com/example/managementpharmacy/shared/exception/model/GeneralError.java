package com.example.managementpharmacy.shared.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// This class is used to return a simple error message in the response of an API or during exception handling.
// It contains only one field (`message`) to store the error message, making it ideal for situations
// where a basic and straightforward error message is needed without additional details or complexity.


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralError {

    private String message;

}
