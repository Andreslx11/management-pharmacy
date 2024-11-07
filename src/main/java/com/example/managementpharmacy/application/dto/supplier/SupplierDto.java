package com.example.managementpharmacy.application.dto.supplier;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private Long id;
    private String companyName;
    private String contact;
    private String phone;
    private String email;
    private String nit;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private State state;

}
