package com.example.managementpharmacy.application.mapper;


import com.example.managementpharmacy.application.dto.product.*;
import com.example.managementpharmacy.persistence.entity.Product;


import org.mapstruct.*;

// MapStruct annotations
// @Mapper: Indicates that this interface will act as a mapper in MapStruct.
// The value in the parentheses (componentModel) indicates that MapStruct will generate
// an implementation of the mapper as a Spring managed bean.
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SupplierMapper.class})
public interface ProductMapper {

    // App ==> client

    @Mapping(source = "supplier.id", target = "supplierId")
    ProductDto toDto(Product product);

    ProductSmallDto toSmallDto(Product product);

    ProductSavedDto toSaveDto(Product product);

    // Client ==> app
    Product toEntity(ProductBodyDto productoCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntity(@MappingTarget
                         Product product,
                         ProductBodyDto productBodyDto
    );

}
