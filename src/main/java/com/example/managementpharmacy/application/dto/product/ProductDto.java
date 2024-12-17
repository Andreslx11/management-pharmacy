package com.example.managementpharmacy.application.dto.product;


import com.example.managementpharmacy.persistence.enums.entity.Concentration;
import com.example.managementpharmacy.persistence.enums.entity.Presentation;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


//@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    // DTO with all attributes
    private Long id;
    private String tradeName;
    private String genericName;
    private String laboratory;
    private Presentation presentation;
    private Concentration concentration;
    private Integer stock;
    private BigDecimal salePrice;
    private String expirationDate;
    private String category;
    private String invimaRegistration;
    private Long supplierId;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private State state;
    private String description;
    private String contraindications;
    private String urlkey;

}
