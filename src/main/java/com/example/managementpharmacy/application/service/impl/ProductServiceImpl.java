package com.example.managementpharmacy.application.service.impl;


import com.example.managementpharmacy.application.dto.product.*;
import com.example.managementpharmacy.application.mapper.ProductMapper;
import com.example.managementpharmacy.application.service.ProductService;
import com.example.managementpharmacy.persistence.entity.Product;
import com.example.managementpharmacy.persistence.entity.Supplier;
import com.example.managementpharmacy.persistence.enums.sortfield.ProductSortField;
import com.example.managementpharmacy.persistence.repository.ProductRepository;
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
import java.util.Optional;

import static com.example.managementpharmacy.shared.util.StringHelper.buildSlugsKeywords;

// Lombok annotation to generate constructors and inject IoC beans.
@RequiredArgsConstructor

// Spring Sterotype annotation
@Service
public class ProductServiceImpl extends PagingAndSortingBuilder implements ProductService {


    /*
     With the final reserved word we indicate that the attributes cannot be reassigned
     after their initialization. In the context of IoC (Inversion of Control),    this
     guarantees that the dependencies injected in the constructor do not change during
     the object's life cycle.
     */
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductSmallDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) throws DataNotFoundException {
        return productMapper.toDto(getProductById(id));
    }

    @Transactional
    @Override
    public ProductSavedDto create(ProductBodyDto productCreate) {
        if (!supplierRepository.existsById(productCreate.getSupplierId())) {
            throw new DataNotFoundException("Supplier with ID " +
                    productCreate.getSupplierId() + " does not exist.");
        }
        Product product = productMapper.toEntity(productCreate);
        product.setCreationDate(LocalDate.now());
        product.setState(State.ENABLED);
        product.setUrlkey(buildSlugsKeywords(product.getTradeName()));
        return saveAndMapSaveDto(product);
    }

    @Transactional
    @Override
    public ProductSavedDto update(Long id, ProductBodyDto productBodyDto) throws DataNotFoundException {
        // Find the product by ID
        Product product = getProductById(id);

        // If the DTO has a new `supplierId`, find and update the supplier
        Optional.ofNullable(productBodyDto.getSupplierId())
                .ifPresent(supplierId -> {
                    Supplier supplier = supplierRepository.findById(supplierId)
                            .orElseThrow(() -> new DataNotFoundException("Supplier not found with ID: " + supplierId));
                    product.setSupplier(supplier);
                });

        // Map the other fields from the DTO to the existing product
        productMapper.updateEntity(product, productBodyDto);

        product.setUrlkey(buildSlugsKeywords(product.getTradeName()));

        // Update the modification date
        product.setUpdateDate(LocalDate.now());

        // Save and return the updated product
        return saveAndMapSaveDto(product);
    }

    @Transactional
    @Override
    public ProductSavedDto disable(Long id) throws DataNotFoundException {
        Product product = getProductById(id);
        product.setState(State.DISABLED);
        return saveAndMapSaveDto(product);
    }

    @Override
    public List<ProductSmallDto> findByState(String state) {
        return productRepository.findByState(state)
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<ProductSmallDto> findByTradeName(String name) {
        List<Product> products = productRepository.findByTradeName(name);
        return products
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<ProductSmallDto> findAllByFilters(String name, String state) {
        List<Product> products = productRepository.findAllByFilters(name, state);
        return products
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public PageResponse<ProductDto> findAllPaginated(int page, int size) {
        // variables
        Pageable pageable = PageRequest.of(page - 1, size);

        // process
        // Get the total number of pages available from the product repository
        Page<Product> productPage = productRepository.findAll(pageable);

        // result
        // Build and return the response with the product data
        return buildPageResponse(productPage, productMapper::toDto);
    }

    @Override
    public PageResponse<ProductDto> paginatedSearch(ProductFilterDto filter) {
        // variables
        String column = ProductSortField.getSqlColumn(filter.getSortField());
        Pageable pageable = buildPageable(filter, column);

        // process
        Page<Product> productPage = productRepository.paginatedSearch(
                filter.getTradeName(),
                filter.getExpirationDateFrom(),
                filter.getExpirationDateTo(),
                filter.getSalePriceFrom(),
                filter.getSalePriceTo(),
                filter.getState().getValue(),
                pageable);

        // result
        return buildPageResponse(productPage, productMapper::toDto);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()
                        -> new DataNotFoundException("Product not found with ID: " + id));
    }

    private ProductSavedDto saveAndMapSaveDto(Product product) {
        return productMapper.toSaveDto(productRepository.save(product));
    }
}
