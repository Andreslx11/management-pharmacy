package com.example.managementpharmacy.shared.state.converter;

import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State state) {
        if (state == null) {
            return null;
        }
        return state.getValue();  // Devuelve 'A' o 'E' según el enum
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return switch (dbData) {
            case "A" -> State.ENABLED;
            case "E" -> State.DISABLED;
            default -> throw new IllegalArgumentException("Valor desconocido: " + dbData);
        };
    }
}