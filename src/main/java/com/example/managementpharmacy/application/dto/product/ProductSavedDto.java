package com.example.managementpharmacy.application.dto.product;


import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSavedDto {

    private Long id;
    private State state;

}
