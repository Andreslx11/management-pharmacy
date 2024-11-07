package com.example.managementpharmacy.persistence.enums.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

//Lombok
@Getter
@AllArgsConstructor

public enum Presentation {

        // Solid forms
        TABLET("comprimido"),
        CAPSULE("cápsula"),
        POWDER("polvo"),
        GRANULE("granulado"),
        OVULE("óvulo"),
        SUPPOSITORY("supositorio"),

        // Liquid forms
        SOLUTION("solución"),
        SUSPENSION("suspensión"),
        EMULSION("emulsión"),
        SYRUP("jarabe"),
        ELIXIR("elixir"),

        // Others
        TRANSDERMAL_PATCH("parche transdérmico"),
        INHALER("inhalador"),
        GEL("gel"),
        CREAM("crema"),
        OINTMENT("ungüento"),
        OTHER("otra");

        private final String description;
}

