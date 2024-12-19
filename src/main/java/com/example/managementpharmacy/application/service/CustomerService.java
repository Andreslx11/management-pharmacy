package com.example.managementpharmacy.application.service;

import com.example.managementpharmacy.application.dto.customer.CustomerBodyDto;
import com.example.managementpharmacy.application.dto.customer.CustomerDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSaveDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSmallDto;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;

import java.util.List;

public interface CustomerService {

    // CRUD
    List<CustomerSmallDto> findAll();

    CustomerDto findById(Long id) throws DataNotFoundException;

    CustomerSaveDto create(CustomerBodyDto customerBodyDto);

    CustomerSaveDto update(Long id, CustomerBodyDto customerBodyDto) throws DataNotFoundException;

    CustomerSaveDto disable(Long id) throws DataNotFoundException;


    //

    List<CustomerSmallDto> findByState(String state) throws DataNotFoundException;


}
