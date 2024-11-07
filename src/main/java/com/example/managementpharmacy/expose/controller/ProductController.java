package com.example.managementpharmacy.expose.controller;

import com.example.managementpharmacy.application.dto.product.*;
import com.example.managementpharmacy.application.service.ProductService;
import com.example.managementpharmacy.shared.constant.StatusCode;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.exception.model.ArgumentNotValidError;
import com.example.managementpharmacy.shared.exception.model.GeneralError;
import com.example.managementpharmacy.shared.page.PageResponse;
import com.example.managementpharmacy.shared.state.enums.State;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Lombok annotations
@RequiredArgsConstructor

// Spring Stereotype annotation
@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;


    @ApiResponse(responseCode = StatusCode.OK, description = "List of all products")
    @GetMapping
    public ResponseEntity<List<ProductSmallDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAll());
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "Product by id")
    @ApiResponse(responseCode = StatusCode.NOT_FOUND,
            description = "Product not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_NDJSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findById(id));
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "List of products by state")
    @GetMapping("/state/{state}")
    public ResponseEntity<List<ProductSmallDto>> findByState(@PathVariable("state") State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByState(state.getValue()));
    }

    @ApiResponse(responseCode = StatusCode.OK, description = "Product by trade name trade")
    @GetMapping("/trade-name/{tradeName}")
    public ResponseEntity<List<ProductSmallDto>> findByNameTrade(@PathVariable("tradeName") String tradeName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByTradeName(tradeName));
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "List of product by filters")
    @GetMapping("/filters")
    public ResponseEntity<List<ProductSmallDto>> findAllByFilters(
            @RequestParam(value = "tradeName", required = false) String tradeName,
            @RequestParam(value = "state", required = false) State state) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAllByFilters(tradeName, state.getValue()));

    }

    @ApiResponse(responseCode = StatusCode.OK, description = "List of products paginated")
    @GetMapping("/paginated")
    public ResponseEntity<PageResponse<ProductDto>> findAllPaginated(
            @NotNull(message = " Page is required ")
            @Min(value = 1, message = "Page must be  a number positive")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "Size is required")
            @Min(value = 1, message = "Size must be a number positive")
            @RequestParam(name = "page", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAllPaginated(page, size));

    }


    @ApiResponse(responseCode = StatusCode.OK, description = "List of products paginated by filters")
    @GetMapping("/paginated-search")
    public ResponseEntity<PageResponse<ProductDto>> paginatedSearch(
            @NotNull(message = " Page is required ")
            @Min(value = 1, message = "Page must be  a number positive")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "Size is required")
            @Min(value = 1, message = "Size must be a number positive")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(value = "tradeName", required = false) String tradeName,

            @Parameter(description = "Filters products expiring after the specified date")
            @RequestParam(value = "expirationDateFrom", required = false) LocalDate expirationDateFrom,

            @Parameter(description = "Filter products up to a specific date")
            @RequestParam(value = "expirationDateTo", required = false) LocalDate expirationDateTo,

            @RequestParam(value = "salePriceFrom", required = false) BigDecimal salePriceFrom,
            @RequestParam(value = "salePriceTo", required = false) BigDecimal salePriceTo,

            @Parameter(description = "State must be 'ENABLED' or 'DISABLED' (default: 'ENABLED')")
            @RequestParam(value = "state", required = false, defaultValue = "ENABLED") State state,

            @Parameter(description = "Sort field must be  'id', 'tradeName', 'state' or 'creationDate'" +
                    " (default: 'id')")
            @Pattern(regexp = "^(id|tradeName|state|creationDate)$", message = "Sort field must be  'id'," +
                    " 'tradeName', 'state' or 'creationDate'")
            @RequestParam(value = "sortField", required = false) String sortField,


            @Parameter(description = "Sort order must be 'ASC' or 'DESC' (default: 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "Sort order must be 'ASC' or 'DESC' (default: 'DESC')")
            @RequestParam(value = "sortOrder", required = false) String sortOrder


    ) {

        ProductFilterDto filter = ProductFilterDto.builder()
                .page(page)
                .size(size)
                .tradeName(tradeName)
                .expirationDateFrom(expirationDateFrom)
                .expirationDateTo(expirationDateTo)
                .salePriceFrom(salePriceFrom)
                .salePriceTo(salePriceTo)
                .state(state)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.paginatedSearch(filter));
    }


    @ApiResponse(responseCode = StatusCode.CREATED, description = "Product created")
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            description = "Invalid  data",
            content = @Content(
                    mediaType = MediaType.APPLICATION_NDJSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<ProductSavedDto> create(@Valid @RequestBody ProductBodyDto productBodyDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(productBodyDto));

    }


    @ApiResponse(responseCode = StatusCode.OK, description = "Product updated")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Product not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_NDJSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductSavedDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody
            ProductBodyDto productBodyDto) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.update(id, productBodyDto));
    }


    @ApiResponse(responseCode = StatusCode.OK, description = "Product disabled")
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            description = "Product not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_NDJSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            ))
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductSavedDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.disable(id));
    }


}

