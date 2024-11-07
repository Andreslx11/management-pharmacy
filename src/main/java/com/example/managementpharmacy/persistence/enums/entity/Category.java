package com.example.managementpharmacy.persistence.enums.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Lombok
@Getter
@AllArgsConstructor
public enum Category {

    MEDICATIONS("Medicamentos"),
    DERMOCOSMETICS("Dermocosmética"),
    ORAL_HYGIENE("Higiene bucal"),
    NUTRITION("Nutrición"),
    ORTHOPEDICS("Ortopedia"),
    CHILDREN("Infantil"),
    OTHER("Otra");

    private final String description;

}
