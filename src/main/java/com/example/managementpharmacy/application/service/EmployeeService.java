package com.example.managementpharmacy.application.service;

import com.example.managementpharmacy.application.dto.employee.*;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;

import java.util.List;

public interface EmployeeService {

    // CRUD
    List<EmployeeSmallDto> findAll();

    EmployeeDto findById(Long id) throws DataNotFoundException;

    EmployeeSavedDto create(EmployeeBodyDto employeeCreate);

    EmployeeSavedDto update(Long id, EmployeeBodyDto employeeBodyDto) throws DataNotFoundException;

    EmployeeSavedDto disable(Long id) throws DataNotFoundException;


    //
    List<EmployeeSmallDto> findByState(String state) throws DataNotFoundException;

    PageResponse<EmployeeDto> paginatedSearch(EmployeeFilterDto filter);


}
