package com.example.managementpharmacy.application.dto.userentity;


import com.example.managementpharmacy.shared.state.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSavedDto {

    private Long id;
    private String username;
    private State state;

}
