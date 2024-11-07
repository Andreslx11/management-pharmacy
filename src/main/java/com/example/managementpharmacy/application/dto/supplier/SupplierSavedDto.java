package com.example.managementpharmacy.application.dto.supplier;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierSavedDto {

    private Long id;
    private State  state;
}
