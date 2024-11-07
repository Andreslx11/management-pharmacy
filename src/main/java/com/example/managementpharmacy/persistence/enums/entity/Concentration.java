package com.example.managementpharmacy.persistence.enums.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Concentration {

    MG_ML("mg/ml"),
    MG_TABLET("mg/comprimido"),
    IU_ML("UI/ml"),
    IU_TABLET("UI/comprimido"),
    PERCENTAGE("%"),
    PPM("ppm"),
    OTHER("Otra");

    private final String unit;
}
