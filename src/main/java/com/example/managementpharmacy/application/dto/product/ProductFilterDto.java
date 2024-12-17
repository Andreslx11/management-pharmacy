package com.example.managementpharmacy.application.dto.product;


import com.example.managementpharmacy.shared.page.PageableRequest;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductFilterDto extends PageableRequest {

    private String tradeName;
    private LocalDate expirationDateFrom;
    private LocalDate expirationDateTo;
    private BigDecimal salePriceFrom;
    private BigDecimal salePriceTo;
    private State state;
}
