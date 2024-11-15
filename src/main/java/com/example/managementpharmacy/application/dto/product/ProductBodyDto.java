package com.example.managementpharmacy.application.dto.product;

import com.example.managementpharmacy.persistence.enums.entity.Category;
import com.example.managementpharmacy.persistence.enums.entity.Concentration;
import com.example.managementpharmacy.persistence.enums.entity.Presentation;

import com.example.managementpharmacy.shared.util.Create;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBodyDto {

    // DTO  to create and update


    @NotBlank(message = "Trade name is required", groups = { Create.class})
    @Size(min = 3, max = 255, message = "Trade name must be between 3 and 255 characters")
    private String tradeName;

    private String genericName;

    @NotBlank(message = "Laboratory is required", groups = { Create.class})
    @Size(min = 3, max = 225, message = "Laboratory must be between 3 and 255 characters")
    private String laboratory;

    @NotNull(message = "Presentation is required", groups = { Create.class})
    private Presentation presentation;

    @NotNull(message = "Concentration is required", groups = { Create.class})
    private Concentration concentration;

    @NotNull(message = "Stock is required", groups = { Create.class})
    private Integer stock;

    @NotNull(message = "Sale price is required", groups = { Create.class})
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    private BigDecimal salePrice;

    @NotNull(message = "Expiration date is required", groups = { Create.class})
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be yyyy-MM-dd")
    private String expirationDate;

    @NotNull(message = "Category is required", groups = { Create.class})
    private Category category;

    @NotBlank(message = "Invima registration is required", groups = { Create.class})
    @Size(min = 5, max = 225, message = "Invima registration must be between 5 and 255 characters")
    private String invimaRegistration;

    @NotNull(message = "Supplier ID is required", groups = { Create.class})
    @Positive(message = "Supplier ID must be a positive number")
    private Long supplierId;

    @NotBlank(message = "Description is required", groups = { Create.class})
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Contraindications are required", groups = { Create.class})
    @Size(max = 255, message = "Contraindications must be less than 255 characters")
    private String contraindications;
}