package com.example.managementpharmacy.application.dto.employee;


import com.example.managementpharmacy.persistence.enums.entity.DocumentType;
import com.example.managementpharmacy.shared.util.Create;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBodyDto {

    // Dto to create and update

    @NotBlank(message = "First name cannot be blank", groups = { Create.class})
    private String firstName;

    @NotBlank(message = "Last name cannot be blank", groups = { Create.class})
    private String lastName;

    @NotNull(message = "Document type cannot be null", groups = { Create.class})
    private DocumentType documentType;

    @NotBlank(message = "Document number cannot be blank", groups = { Create.class})
    private String documentNumber;

    @NotBlank(message = "Phone number cannot be blank", groups = { Create.class})
    @Pattern(regexp = "\\d{11}", message = "Phone number must be a 11-digit number")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank", groups = { Create.class})
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role id cannot be null", groups = { Create.class})
    private Long roleId;

    @NotNull(message = "Contract start date is required", groups = { Create.class})
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be yyyy-MM-dd")
    private String contractStartDate;

    @NotNull(message = "Contract end date is required", groups = { Create.class})
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be yyyy-MM-dd")
    private String contractEndDate;


}
