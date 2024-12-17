package com.example.managementpharmacy.expose.controller;


import com.example.managementpharmacy.application.dto.employee.*;
import com.example.managementpharmacy.application.service.EmployeeService;
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


// Stereotype
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    @ApiResponse(responseCode = StatusCode.OK, description = "List of all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeSmallDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.findAll());
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Employee by id")
    @ApiResponse(responseCode = StatusCode.NOT_FOUND,
            description = "Employe not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.findById(id));
    }

    @ApiResponse(responseCode = StatusCode.CREATED, description = "Empleado created")
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid  data",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<EmployeeSavedDto> create(@Valid
                                                   @Validated(Create.class)
                                                   @RequestBody
                                                   EmployeeBodyDto employeeBodyDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.create(employeeBodyDto));

    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Product employee")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Employee not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeSavedDto> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody
                                                   EmployeeBodyDto employeeBodyDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.update(id, employeeBodyDto));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Employee disabled")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Employee not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeSavedDto> disabled(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.disable(id));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of employee by state")
    @GetMapping("/state/{state}")
    public ResponseEntity<List<EmployeeSmallDto>> findByState(@PathVariable("state") State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.findByState(state.getValue()));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of employees paginated by filters")
    @GetMapping("/paginated-search")
    public ResponseEntity<PageResponse<EmployeeDto>> paginatedSearch(
            @NotNull(message = "Page is required")
            @Min(value = 1, message = "Page must be a positive number")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "Size is required")
            @Min(value = 1, message = "Size must be a positive number")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "document_number", required = false) String documentNumber,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "email", required = false) String email,

            @Parameter(description = "Filter employees with contracts starting from the specified date")
            @RequestParam(value = "contract_start_date", required = false) LocalDate contractStartDate,

            @Parameter(description = "Filter employees with contracts ending up to the specified date")
            @RequestParam(value = "contract_end_date", required = false) LocalDate contractEndDate,

            @Parameter(description = "State must be 'ENABLED' or 'DISABLED' (default: 'ENABLED')")
            @RequestParam(value = "state", required = false, defaultValue = "ENABLED") State state,

            @Parameter(description = "Sort field must be 'id', 'firstName', 'lastName', 'email', or 'contractStartDate' (default: 'id')")
            @Pattern(regexp = "^(id|firstName|lastName|email|contractStartDate)$", message = "Sort field must be 'id', 'firstName', 'lastName', 'email', or 'contractStartDate'")
            @RequestParam(value = "sort_field", required = false) String sortField,

            @Parameter(description = "Sort order must be 'ASC' or 'DESC' (default: 'DESC')")
            @Pattern(regexp = "^(ASC|DESC)$", message = "Sort order must be 'ASC' or 'DESC' (default: 'DESC')")
            @RequestParam(value = "sort_order", required = false) String sortOrder
    ) {

        EmployeeFilterDto filter = EmployeeFilterDto.builder()
                .page(page)
                .size(size)
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .documentNumber(documentNumber)
                .role(role)
                .email(email)
                .contractStartDate(contractStartDate)
                .contractEndDate(contractEndDate)
                .state(state)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.paginatedSearch(filter));
    }

}
