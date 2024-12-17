package com.example.managementpharmacy.expose.controller;


import com.example.managementpharmacy.application.dto.supplier.*;
import com.example.managementpharmacy.application.service.SupplierService;
import com.example.managementpharmacy.shared.constant.StatusCode;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import com.example.managementpharmacy.shared.page.PageResponse;
import com.example.managementpharmacy.shared.state.enums.State;
import com.example.managementpharmacy.shared.util.Create;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


// Lombok annotation
@RequiredArgsConstructor

// Spring Stereotype annotation
@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @ApiResponse(responseCode = StatusCode.OK, description = "List of all suppliers")
    @GetMapping
    public ResponseEntity<List<SupplierSmallDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findAll());
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Supplier by id")
    @ApiResponse(responseCode = StatusCode.NOT_FOUND,
            description = "Supplier not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @GetMapping(("/{id}"))
    public ResponseEntity<SupplierDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findById(id));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of suppliers by state")
    @GetMapping("/state/{state}")
    public ResponseEntity<List<SupplierSmallDto>> findByState(@PathVariable("state") State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findByState(state.getValue()));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Supplier by company name")
    @GetMapping("/company-name/{companyName}")
    public ResponseEntity<List<SupplierSmallDto>> findByCompanyName(@PathVariable("companyName")
                                                                    String companyName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findByName(companyName));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of supplier by filters")
    @GetMapping("/filters")
    public ResponseEntity<List<SupplierSmallDto>> findAllByfilters(
            @RequestParam(value = "company_name", required = false) String companyName,
            @RequestParam(value = "state", defaultValue = "ENABLED", required = false) State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findAllByFilters(companyName, state.getValue()));

    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of supplirs  paginated")
    @GetMapping("/paginated")
    public ResponseEntity<PageResponse<SupplierDto>> findAllPaginated(
            @NotNull(message = "Page is required")
            @Min(value = 1, message = "Page must be a number positive")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "Size is requerid")
            @Min(value = 10, message = "Size must be a number positive")
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.findAllPaginated(page, size));

    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List od suppliers paginated  by filters")
    @GetMapping("/paginated-search")
    public ResponseEntity<PageResponse<SupplierDto>> paginatedSearch(
            @NotNull(message = "Page is required")
            @Min(value = 1, message = "Page must be a number positive")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "Size is requerid")
            @Min(value = 10, message = "Size must be a number positive")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(value = "company_name", required = false) String companyName,

            @RequestParam(value = "contact", required = false) String contact,

            @RequestParam(value = "phone", required = false) String phone,

            @RequestParam(value = "email", required = false) String email,

            @RequestParam(value = "nit", required = false) String nit,

            @Parameter(description = "State must be 'ENABLED' or 'DISABLED' (default: 'ENABLED')")
            @RequestParam(value = "state", defaultValue = "ENABLED", required = false) State state,

            @Parameter(description = "Filters suppliers created on or after the specified date")
            @RequestParam(value = "creation_date_from", required = false) LocalDate creationDateFrom,

            @Parameter(description = "Filters suppliers created on or before the specified date")
            @RequestParam(value = "creation_date_To", required = false) LocalDate creationDateTo,


            @Parameter(description = "Sort field must be  'id', 'companyName', 'state' or 'creationDate' " +
                    " (default: 'id')  ")
            @Pattern(regexp = "^(id|companyName|state|creationDate)$", message = "Sort field must be 'id'," +
                    " 'companyName', 'state' or 'creationDate' ")
            @RequestParam(value = "sort_field", required = false) String sortField,

            @Parameter(description = "Sort order must be 'ASC' or 'DESC' (default: 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "Sort order must be 'ASC' or 'DESC' (default: 'DESC')")
            @RequestParam(value = "sort_order", required = false) String sortOrder
    ) {

        SupplierFilterDto filter = SupplierFilterDto.builder()
                .page(page)
                .size(size)
                .companyName(companyName)
                .contact(contact)
                .phone(phone)
                .email(email)
                .nit(nit)
                .state(state)
                .creationDateFrom(creationDateFrom)
                .creationDateTo(creationDateTo)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.paginatedSearch(filter));

    }

    @ApiResponse(responseCode = StatusCode.CREATED, description = "Supplier created")
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid date",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<SupplierSavedDto> create(
            @Valid @Validated(Create.class) @RequestBody
            SupplierBodyDto supplierBodyDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(supplierService.create(supplierBodyDto));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Supplier updated")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Supplier not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            ))
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<SupplierSavedDto> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody
                                                   SupplierBodyDto supplierBodyDto) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.update(id, supplierBodyDto));

    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Supplier disabled")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Supplier not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @DeleteMapping("/{id}")
    public ResponseEntity<SupplierSavedDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(supplierService.disable(id));
    }
}
