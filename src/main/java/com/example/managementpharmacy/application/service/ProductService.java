package com.example.managementpharmacy.application.service;

import com.example.managementpharmacy.application.dto.product.*;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;
import java.util.List;

// Esta interface es para cumplir el principio solid de inversion de dependencia

public interface ProductService {

    // CRUD principal
    List<ProductSmallDto> findAll();

    ProductDto findById(Long id)  throws DataNotFoundException;

    ProductSavedDto create(ProductBodyDto productCreate);

    ProductSavedDto update(Long id, ProductBodyDto  productBodyDto) throws DataNotFoundException;

    ProductSavedDto disable(Long id) throws DataNotFoundException;


    //
    List<ProductSmallDto> findByState(String state);

    List<ProductSmallDto> findByTradeName(String name);

    List<ProductSmallDto> findAllByFilters(String name, String state);

    PageResponse<ProductDto> findAllPaginated(int page, int size);

    PageResponse<ProductDto> paginatedSearch(ProductFilterDto filter);
}
