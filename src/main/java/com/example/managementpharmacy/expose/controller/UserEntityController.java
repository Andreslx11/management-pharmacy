package com.example.managementpharmacy.expose.controller;

import com.example.managementpharmacy.application.dto.userentity.UserBodyDto;
import com.example.managementpharmacy.application.dto.userentity.UserDto;
import com.example.managementpharmacy.application.dto.userentity.UserSavedDto;
import com.example.managementpharmacy.application.dto.userentity.UserSmallDto;
import com.example.managementpharmacy.application.service.UserEntityService;
import com.example.managementpharmacy.shared.constant.StatusCode;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import com.example.managementpharmacy.shared.util.Create;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.transaction.Transactional;
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
@RequestMapping("/users")
public class UserEntityController {


    private final UserEntityService userService;


    @ApiResponse(responseCode = StatusCode.OK, description = "List of all users")
    @GetMapping
    public ResponseEntity<List<UserSmallDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @ApiResponse(responseCode = StatusCode.OK, description = " User by id")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "User not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findId(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }


    @SecurityRequirements(value = {})
    @ApiResponse(responseCode = StatusCode.CREATED, description = "User created")
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid data",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Profile not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @Transactional
    @PostMapping
    public ResponseEntity<UserSavedDto> create(@Valid @Validated(Create.class) @RequestBody UserBodyDto userBodyDto) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(userBodyDto));
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "User updated")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "User not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid data",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserSavedDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserBodyDto userBodyDto) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.update(id, userBodyDto));
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "User disabled")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "User not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<UserSavedDto> disable(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.disable(id));
    }

}

