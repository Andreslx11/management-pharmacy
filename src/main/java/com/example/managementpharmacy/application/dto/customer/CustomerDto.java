package com.example.managementpharmacy.application.dto.customer;


import com.example.managementpharmacy.persistence.enums.entity.DocumentType;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private DocumentType documentType;
    private String documentNumber;
    private String address;
    private String phone;
    private String email;
    private String birthDate;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private State state;

}