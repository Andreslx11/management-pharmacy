package com.example.managementpharmacy.application.dto.customer;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSmallDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
