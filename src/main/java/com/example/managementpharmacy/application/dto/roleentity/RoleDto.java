
package com.example.managementpharmacy.application.dto.roleentity;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String roleName;
    private String description;
    private LocalDate creationDate;
    private LocalDate updateDate;
}
