package com.example.managementpharmacy.application.dto.product;

import com.example.managementpharmacy.persistence.enums.entity.Category;
import com.example.managementpharmacy.persistence.enums.entity.Concentration;
import com.example.managementpharmacy.persistence.enums.entity.Presentation;

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


    @NotBlank(message = "Trade name is required")
    @Size(min = 3, max = 255, message = "Trade name must be between 3 and 255 characters")
    private String tradeName;

    private String genericName;

    @NotBlank(message = "Laboratory is required")
    @Size(min = 3, max = 225, message = "Laboratory must be between 3 and 255 characters")
    private String laboratory;

    @NotNull(message = "Presentation is required")
    private Presentation presentation;

    @NotNull(message = "Concentration is required")
    private Concentration concentration;

    @NotNull(message = "Stock is required")
    private Integer stock;

    @NotNull(message = "Sale price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0")
    private BigDecimal salePrice;

    @NotNull(message = "Expiration date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be dd-MM-yyyy")
    private String expirationDate;

    @NotNull(message = "Category is required")
    private Category category;

    @NotBlank(message = "Invima registration is required")
    @Size(min = 5, max = 225, message = "Invima registration must be between 5 and 255 characters")
    private String invimaRegistration;

    @NotNull(message = "Supplier ID is required")
    @Positive(message = "Supplier ID must be a positive number")
    private Long supplierId;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Contraindications are required")
    @Size(max = 255, message = "Contraindications must be less than 255 characters")
    private String contraindications;
}