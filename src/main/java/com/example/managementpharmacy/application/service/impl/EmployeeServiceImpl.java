package com.example.managementpharmacy.application.service.impl;


import com.example.managementpharmacy.application.dto.employee.*;
import com.example.managementpharmacy.application.mapper.EmployeeMappeer;
import com.example.managementpharmacy.application.service.EmployeeService;
import com.example.managementpharmacy.persistence.entity.Employee;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import com.example.managementpharmacy.persistence.enums.sortfield.EmployeeSortField;
import com.example.managementpharmacy.persistence.repository.EmployeeRepository;
import com.example.managementpharmacy.persistence.repository.RoleEntityRepository;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.page.PageResponse;
import com.example.managementpharmacy.shared.page.PagingAndSortingBuilder;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Service layer implementation for managing employee operations.
// This class interacts with the EmployeeRepository to handle CRUD operations
// and uses EmployeeMappeer to convert between entities and DTOs.

// Lombok annotation to generate constructors and inject IoC beans.
@RequiredArgsConstructor

// Spring stereotype
@Service
public class EmployeeServiceImpl extends PagingAndSortingBuilder implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final EmployeeMappeer employeeMappeer;
    private final RoleEntityRepository roleRepository;

    @Override
    public List<EmployeeSmallDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMappeer::toSmallDto)
                .toList();
    }

    @Override
    public EmployeeDto findById(Long id) throws DataNotFoundException {
        return employeeMappeer.toDto(getEmployeeById(id));
    }

    @Transactional
    @Override
    public EmployeeSavedDto create(EmployeeBodyDto employeeCreate) {
        RoleEntity role = roleRepository.findById(employeeCreate.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role ID "
                        + employeeCreate.getRoleId() + " does not exist"));

        Employee employee = employeeMappeer.toEntity(employeeCreate);
        employee.setRole(role);
        employee.setCreationDate(LocalDate.now());
        employee.setState(State.ENABLED);
        return saveAndMapSaveDto(employee);
    }

    @Transactional
    @Override
    public EmployeeSavedDto update(Long id, EmployeeBodyDto employeeBodyDto) throws DataNotFoundException {
        if (!roleRepository.existsById(employeeBodyDto.getRoleId())) {
            throw new DataNotFoundException("Role ID does not exist");
        }
        Employee employee = employeeMappeer.updateEntity(getEmployeeById(id), employeeBodyDto);
        employee.setUpdateDate(LocalDate.now());
        return saveAndMapSaveDto(employee);
    }

    @Transactional
    @Override
    public EmployeeSavedDto disable(Long id) throws DataNotFoundException {
        Employee employee = getEmployeeById(id);
        employee.setState(State.DISABLED);
        return saveAndMapSaveDto(employee);
    }

    @Override
    public List<EmployeeSmallDto> findByState(String state) {
        return employeeRepository.findByState(state)
                .stream()
                .map(employeeMappeer::toSmallDto)
                .toList();
    }

    @Override
    public PageResponse<EmployeeDto> paginatedSearch(EmployeeFilterDto filter) {
        // variables
        String column = EmployeeSortField.getSqlColumn(filter.getSortField());
        Pageable pageable = buildPageable(filter, column);

        // process
        Page<Employee> employeePage = employeeRepository.paginatedSearch(
                filter.getId(),
                filter.getFirstName(),
                filter.getLastName(),
                filter.getDocumentNumber(),
                filter.getRole(),
                filter.getEmail(),
                filter.getContractStartDate(),
                filter.getContractEndDate(),
                filter.getState().getValue(),
                pageable
        );

        return buildPageResponse(employeePage, employeeMappeer::toDto);
    }

    private Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Employee not found with ID: " + id));
    }

    private EmployeeSavedDto saveAndMapSaveDto(Employee employee) {
        return employeeMappeer.toSaveDto(employeeRepository.save(employee));
    }

}
