package com.example.managementpharmacy.application.dto.userentity;



import lombok.*;

// Lomok annotation
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurityDto extends UserDto {

    private String tokenAccess;

}
