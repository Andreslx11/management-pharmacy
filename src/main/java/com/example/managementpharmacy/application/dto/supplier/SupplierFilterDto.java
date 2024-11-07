package com.example.managementpharmacy.application.dto.supplier;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierFilterDto {

        private String companyName;
        private State state;

}
