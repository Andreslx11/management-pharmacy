package com.example.managementpharmacy.application.dto.supplier;


import com.example.managementpharmacy.shared.util.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierBodyDto {

    @NotBlank(message="Company name is required", groups = {Create.class})
    @Size(min = 3, max = 100, message ="Company name must be between 3 and 100 characters")
    private String companyName;

    @NotBlank( message = "Contact is required", groups = {Create.class})
    @Size(min = 15, max = 50, message = "Contact  must be between  15 and 50  characters")
    private String contact;

    @NotBlank( message = "Phone is required", groups = { Create.class})
    @Size(min = 7, max = 11, message = " Phone must be between  7 and 11 characters")
    private String phone;

    @NotBlank(message = "Email is required", groups = { Create.class})
    @Size(min = 15, max = 40, message = "Email must be between 15  and 40 characters")
    private String email;

    @NotBlank(message = "Nit is required", groups = { Create.class})
    @Size(min = 15, max = 40, message = "Nit must be between 15  and 40 characters")
    private String nit;

}
