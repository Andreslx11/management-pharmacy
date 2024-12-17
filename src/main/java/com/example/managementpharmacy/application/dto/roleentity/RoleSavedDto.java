package com.example.managementpharmacy.application.dto.roleentity;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSavedDto {

    private Long id;
    private State state;
}