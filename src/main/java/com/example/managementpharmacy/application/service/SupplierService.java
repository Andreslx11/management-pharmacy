package com.example.managementpharmacy.application.service;


import com.example.managementpharmacy.application.dto.supplier.*;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;

import java.util.List;

// This interface is to comply with the solid principle of dependency inversion.
public interface SupplierService {

    // CRUD
    List<SupplierSmallDto> findAll();

    SupplierDto  findById(Long id) throws DataNotFoundException;

    SupplierSavedDto create(SupplierBodyDto supplierBodyDto);

    SupplierSavedDto update(Long id, SupplierBodyDto supplierBodyDto) throws DataNotFoundException;

    SupplierSavedDto disable(Long id) throws DataNotFoundException;

    //
    List<SupplierSmallDto> findByState(String state);

    List<SupplierSmallDto> findByName(String name);

    List<SupplierSmallDto>  findAllByFilters(String state, String name);

    PageResponse<SupplierDto> findAllPaginated(int page, int size);

    PageResponse<SupplierDto> paginatedSearch(SupplierFilterDto supplierFilterDto);


}
