package com.example.managementpharmacy.application.dto.userentity;



import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import lombok.*;




@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSmallDto {

        private Long id;
        private String fullName;
        private  long idEmpleyee;
        private RolePharmacy role;

}

