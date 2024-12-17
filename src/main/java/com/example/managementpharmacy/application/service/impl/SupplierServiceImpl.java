package com.example.managementpharmacy.application.service.impl;

import com.example.managementpharmacy.application.dto.supplier.*;
import com.example.managementpharmacy.application.mapper.SupplierMapper;
import com.example.managementpharmacy.application.service.SupplierService;
import com.example.managementpharmacy.persistence.entity.Supplier;
import com.example.managementpharmacy.persistence.enums.sortfield.SupplierSortField;
import com.example.managementpharmacy.persistence.repository.SupplierRepository;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;
import com.example.managementpharmacy.shared.page.PagingAndSortingBuilder;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.managementpharmacy.shared.util.StringHelper.buildSlugsKeywords;

// Lombok annotation to generate constructors and inject IoC beans.
@RequiredArgsConstructor

// Spring Sterotype annotation
@Service
public class SupplierServiceImpl extends PagingAndSortingBuilder implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;


    @Override
    public List<SupplierSmallDto> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toSmallDto)
                .toList();
    }

    @Override
    public SupplierDto findById(Long id) throws DataNotFoundException {
        return supplierMapper.toDto(getSupplierById(id));
    }

    @Transactional
    @Override
    public SupplierSavedDto create(SupplierBodyDto supplierBodyDto) {
        Supplier supplier = supplierMapper.toEntity(supplierBodyDto);
        supplier.setCreationDate(LocalDate.now());
        supplier.setState(State.ENABLED);
        supplier.setUrlkey(buildSlugsKeywords(supplierBodyDto.getCompanyName()));
        return saveAndMapSaveDto(supplier);
    }

    @Transactional
    @Override
    public SupplierSavedDto update(Long id, SupplierBodyDto supplierBodyDto) throws DataNotFoundException {
        Supplier supplier = getSupplierById(id);
        supplierMapper.updateEntity(supplier, supplierBodyDto);
        supplier.setUrlkey(buildSlugsKeywords(supplier.getCompanyName()));
        supplier.setUpdateDate(LocalDate.now());

        return saveAndMapSaveDto(supplier);
    }

    @Transactional
    @Override
    public SupplierSavedDto disable(Long id) throws DataNotFoundException {
        Supplier supplier = getSupplierById(id);
        supplier.setState(State.DISABLED);
        return saveAndMapSaveDto(supplier);
    }

    @Override
    public List<SupplierSmallDto> findByState(String state) {
        return supplierRepository.findByState(state)
                .stream()
                .map(supplierMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<SupplierSmallDto> findByName(String name) {
        List<Supplier> suppliers = supplierRepository
                .findByCompanyNameContainingIgnoreCase(name);
        return suppliers.stream()
                .map(supplierMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<SupplierSmallDto> findAllByFilters(String name, String state) {
        List<Supplier> suppliers = supplierRepository.findAllByFilters(name, state);
        return suppliers.stream()
                .map(supplierMapper::toSmallDto)
                .toList();
    }

    @Override
    public PageResponse<SupplierDto> findAllPaginated(int page, int size) {
        // variables
        Pageable pageable = PageRequest.of(page - 1, size);

        // process
        // Get the total number of pages available from the product repository
        Page<Supplier> supplierPage = supplierRepository.findAll(pageable);

        // result
        // Build and return the response with the product data
        return buildPageResponse(supplierPage, supplierMapper::toDto);
    }

    @Override
    public PageResponse<SupplierDto> paginatedSearch(SupplierFilterDto filter) {
        // variables
        String column = SupplierSortField.getSqlColumn(filter.getSortField());
        Pageable pageable = buildPageable(filter, column);

        // process
        Page<Supplier> supplierPage = supplierRepository.paginatedSearch(
                filter.getCompanyName(),
                filter.getContact(),
                filter.getPhone(),
                filter.getEmail(),
                filter.getNit(),
                filter.getState().getValue(),
                filter.getCreationDateFrom(),
                filter.getCreationDateTo(),
                pageable);


        // result
        return buildPageResponse(supplierPage, supplierMapper::toDto);
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Supplier not found by id:  " + id));
    }

    private SupplierSavedDto saveAndMapSaveDto(Supplier supplier) {
        return supplierMapper.toSaveDto(supplierRepository.save(supplier));
    }
}
