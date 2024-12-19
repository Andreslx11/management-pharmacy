package com.example.managementpharmacy.application.dto.customer;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveDto {
    private Long id;
    private State state;
}
