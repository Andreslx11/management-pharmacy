package com.example.managementpharmacy.application.dto.employee;


import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSavedDto {
    private Long id;
    private State state;
}
