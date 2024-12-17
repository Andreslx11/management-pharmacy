package com.example.managementpharmacy.application.dto.userentity;

import com.example.managementpharmacy.shared.state.enums.State;
import lombok.*;

import java.time.LocalDate;

// Lombok annotation
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Long employee;
    private String role;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private State state;

}
