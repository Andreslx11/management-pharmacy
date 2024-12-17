package com.example.managementpharmacy.application.dto.supplier;

import com.example.managementpharmacy.shared.page.PageableRequest;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupplierFilterDto extends PageableRequest {

        private String companyName;
        private String contact;
        private String phone;
        private String email;
        private String nit;
        private State state;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate creationDateFrom;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate  creationDateTo;
}
