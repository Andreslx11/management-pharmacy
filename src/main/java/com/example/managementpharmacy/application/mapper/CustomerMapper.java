package com.example.managementpharmacy.application.mapper;


import com.example.managementpharmacy.application.dto.customer.CustomerBodyDto;
import com.example.managementpharmacy.application.dto.customer.CustomerDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSaveDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSmallDto;
import com.example.managementpharmacy.persistence.entity.Customer;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    // Database ==> client

    CustomerDto toDto(Customer customer);

    CustomerSmallDto toSmallDto(Customer customer);

    CustomerSaveDto toSaveDto(Customer customer);

    // Client ==> Database

    Customer toEntity(CustomerBodyDto customerBodyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer update(@MappingTarget
                    Customer customer,
                    CustomerBodyDto customerBodyDto);


}
