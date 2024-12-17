package com.example.managementpharmacy.expose.controller;


import com.example.managementpharmacy.application.dto.userentity.AuthDto;
import com.example.managementpharmacy.application.dto.userentity.UserSecurityDto;
import com.example.managementpharmacy.application.service.UserEntityService;
import com.example.managementpharmacy.shared.constant.StatusCode;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// OpenAPI annotation
@SecurityRequirements(value = {})

// Lombok annotation
@RequiredArgsConstructor

// Stereotype
@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserEntityService userService;

    @ApiResponse(responseCode = StatusCode.OK, description = " Authenticate user")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "User not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid date",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            ))
    @PostMapping
    public ResponseEntity<UserSecurityDto> login(@Valid @RequestBody AuthDto authDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(authDto));
    }


}
