package com.example.managementpharmacy.application.mapper;


import com.example.managementpharmacy.application.dto.supplier.SupplierBodyDto;
import com.example.managementpharmacy.application.dto.supplier.SupplierDto;
import com.example.managementpharmacy.application.dto.supplier.SupplierSavedDto;
import com.example.managementpharmacy.application.dto.supplier.SupplierSmallDto;

import com.example.managementpharmacy.persistence.entity.Supplier;

import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {

    // App ==> client
    SupplierDto toDto(Supplier supplier);


    SupplierSmallDto toSmallDto(Supplier supplier);

    SupplierSavedDto toSaveDto(Supplier supplier);

    // Client ==> app
    Supplier toEntity(SupplierBodyDto supplierBodyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Supplier updateEntity(@MappingTarget
                          Supplier supplier,
                          SupplierBodyDto supplierBodyDto
    );


}
