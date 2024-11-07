package com.example.managementpharmacy.persistence.enums.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

//lombok
@Getter
@AllArgsConstructor

public enum DocumentType {

    CITIZEN_ID("Cedula ciudadania"),
    FOREIGN_ID("Cedula extranjera"),
    PASSPORT("Pasaporte"),
    ID_CARD("Tarjeta identidad"),
    NIT("NIT");

    private final String description;
}

