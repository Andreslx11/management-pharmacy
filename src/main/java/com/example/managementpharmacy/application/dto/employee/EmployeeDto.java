package com.example.managementpharmacy.application.dto.employee;

import com.example.managementpharmacy.persistence.entity.Order;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import com.example.managementpharmacy.persistence.entity.UserEntity;
import com.example.managementpharmacy.persistence.enums.entity.DocumentType;
import com.example.managementpharmacy.shared.state.converter.StateConverter;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private DocumentType documentType;
    private String documentNumber;
    private String phoneNumber;
    private String email;
    private RoleEntity role;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private State state;

}