package com.example.managementpharmacy.application.dto.employee;



import com.example.managementpharmacy.shared.page.PageableRequest;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeFilterDto   extends PageableRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String role;
    private String email;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private State state;
}

