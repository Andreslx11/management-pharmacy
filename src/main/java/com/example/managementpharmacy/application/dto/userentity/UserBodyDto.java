package com.example.managementpharmacy.application.dto.userentity;


import com.example.managementpharmacy.shared.util.Create;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBodyDto {

    @NotNull
    @NotBlank(message = "Username is required", groups = {Create.class})
    @Pattern(regexp = "^.{8,}$",
            message = "Username must be at least 8 characters long.")
    private String username;

    @NotNull
    @NotBlank(message = "Password is required", groups = {Create.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,50}$",
            message = "Password must be 10-50 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

    @NotNull
    @NotBlank(message = "Document number is required", groups = {Create.class})
    @Pattern(regexp = "\\d{8,15}",
            message = "The document number must contain only digits and have a length between 8 and 15 characters.")
    private String documentNumber;

    @Pattern(
            regexp = "^\\d{7,11}$",
            message = "Phone number must contain only digits and be between 7 and 11 characters long."
    )
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message ="Email is not valid")
    private String email;


}
