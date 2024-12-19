package com.example.managementpharmacy.expose.controller;


import com.example.managementpharmacy.application.dto.customer.CustomerBodyDto;
import com.example.managementpharmacy.application.dto.customer.CustomerDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSaveDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSmallDto;
import com.example.managementpharmacy.application.service.impl.CustomerServiceImpl;
import com.example.managementpharmacy.shared.constant.StatusCode;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import com.example.managementpharmacy.shared.state.enums.State;
import com.example.managementpharmacy.shared.util.Create;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// Lombok annotation
@RequiredArgsConstructor

// Stereotype
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;


    @ApiResponse(responseCode = StatusCode.OK, description = "List of all customer")
    @GetMapping
    public ResponseEntity<List<CustomerSmallDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAll());
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Customer by id")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Customer not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findById(id));
    }

    @ApiResponse(responseCode = StatusCode.CREATED, description = "Customer created")
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid data",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<CustomerSaveDto> create(@Valid @Validated(Create.class)
                                                  @RequestBody
                                                  CustomerBodyDto customerBodyDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.create(customerBodyDto));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Customer updated")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Employee not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<CustomerSaveDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody
            CustomerBodyDto customerBodyDto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.update(id, customerBodyDto));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Customer disabled")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Customer not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerSaveDto> diable(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.disable(id));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of employee by state")
    @GetMapping("/state/{state}")
    public ResponseEntity<List<CustomerSmallDto>> findState(@PathVariable("state")
                                                            State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findByState(state.getValue()));
    }

}
