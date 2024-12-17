package com.example.managementpharmacy.application.dto.employee;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSmallDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
}
