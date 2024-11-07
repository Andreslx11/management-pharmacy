package com.example.managementpharmacy.shared.state.enums;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

// Swagger annotation
@Schema(type = "object", oneOf = {State.class}, implementation = State.class)

// Lombok annotations
@Getter

// Jackson annotation
//@JsonSerialize(using = StateSerializer.class)
public enum State {
    ENABLED("A", true) ,
    DISABLED("E", false);


    private final String value;
    private final String name;
    private final boolean enabled;

    State(String value, boolean enabled) {
        this.value = value;
        this.name = this.toString();
        this.enabled = enabled;
    }

}



