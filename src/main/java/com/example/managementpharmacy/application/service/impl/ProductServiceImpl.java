package com.example.managementpharmacy.application.service.impl;


import com.example.managementpharmacy.application.dto.product.*;
import com.example.managementpharmacy.application.mapper.ProductMapper;
import com.example.managementpharmacy.application.service.ProductService;
import com.example.managementpharmacy.persistence.entity.Product;
import com.example.managementpharmacy.persistence.enums.sortfield.ProductSortField;
import com.example.managementpharmacy.persistence.repository.ProductRepository;
import com.example.managementpharmacy.persistence.repository.SupplierRepository;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;
import com.example.managementpharmacy.shared.page.PagingAndSortingBuilder;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


import static com.example.managementpharmacy.shared.util.DateHelper.localDateToString;


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
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(()-> productDataNotFoundException(id));
    }

    
    @Override
    public ProductSavedDto create(ProductBodyDto productCreate) {
        if(!supplierRepository.existsById(productCreate.getSupplierId())){
            throw new DataNotFoundException("Supplier with ID " +
                    productCreate.getSupplierId() + " does not exist.");
        }
        Product product = productMapper.toEntity(productCreate);
        product.setCreationDate(LocalDate.now());
        product.setState(State.ENABLED);
        return productMapper.toSaveDto(productRepository.save(product));
    }

    @Override
    public ProductSavedDto update(Long id, ProductBodyDto productBodyDto) throws DataNotFoundException {
          Product product = productRepository.findById(id)
                  .orElseThrow(() -> productDataNotFoundException(id));

          productMapper.updateEntity(product, productBodyDto);
          product.setUpdateDate(LocalDate.now());

        return productMapper.toSaveDto(productRepository.save(product));
    }

    @Override
    public ProductSavedDto disable(Long id) throws DataNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> productDataNotFoundException(id));

        product.setState(State.DISABLED);

        return  productMapper.toSaveDto(productRepository.save(product));
    }

    @Override
    public List<ProductSmallDto> findByState(String state) {
        return  productRepository.findByState( state)
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<ProductSmallDto> findByTradeName(String name) {
        return productRepository.findByTradeName(name)
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public List<ProductSmallDto> findAllByFilters(String name, String state) {
        return productRepository.findAllByFilters(name, state)
                .stream()
                .map(productMapper::toSmallDto)
                .toList();
    }

    @Override
    public PageResponse<ProductDto> findAllPaginated(int page, int size) {
       // variables
        Pageable pageable = PageRequest.of(page - 1, size);

       // process
        Page<Product>productPage = productRepository.findAll(pageable);

        // result
        return buildPageResponse(productPage, productMapper::toDto);
    }

    @Override
    public PageResponse<ProductDto> paginatedSearch(ProductFilterDto filter) {
        // variables
         String column = ProductSortField.getSqlColumn(filter.getSortField());
         Pageable pageable = buildPageable(filter, column);

        // process
        Page<Product>  productPage = productRepository.paginatedSearch(
                filter.getTradeName(),
                localDateToString(filter.getExpirationDateFrom()),
                localDateToString(filter.getExpirationDateTo()),
                filter.getSalePriceFrom(),
                filter.getSalePriceTo(),
                filter.getState().getValue(),
                pageable);

        // result
        return buildPageResponse(productPage,  productMapper::toDto);
    }




    private static DataNotFoundException productDataNotFoundException(Long id) {
        return new DataNotFoundException("Product not found:  " + id);
    }
}
