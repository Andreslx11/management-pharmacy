package com.example.managementpharmacy.shared.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

/*
 Lombok annotation that marks a class as a utility class. It automatically:
 1. Makes the class final, so it cannot be subclassed.
 2. Adds a private constructor to prevent instantiation of the class.
 3. Makes all methods static, allowing them to be accessed without creating an instance of the class.
*/

@UtilityClass
public class DateHelper {

          public static String localDateToString(LocalDate date){
              return  date != null ? date.toString() : null;
          }
}
