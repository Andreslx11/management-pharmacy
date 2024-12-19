package com.example.managementpharmacy.application.dto.customer;

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
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBodyDto {

    @NotBlank(message = "First name cannot be blank", groups = {Create.class})
    private String firstName;

    @NotBlank(message = "Last name cannot be blank", groups = {Create.class})
    private String lastName;

    @NotNull(message = "Document type is required", groups = {Create.class})
    private DocumentType documentType;

    @NotBlank(message = "Document number cannot be blank", groups = {Create.class})
    @Pattern(regexp = "^\\d+$", message = "Document number should contain only digits")
    private String documentNumber;

    @NotBlank(message = "Address cannot be blank", groups = {Create.class})
    private String address;

    @NotBlank(message = "Phone cannot be blank", groups = {Create.class})
    @Pattern(regexp = "^\\d+$", message = "Phone number should contain only digits")
    private String phone;

    @NotBlank(message = "Email cannot be blank", groups = {Create.class})
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Birth date cannot be blank", groups = {Create.class})
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birth date should follow the pattern YYYY-MM-DD")
    private String birthDate;


}
