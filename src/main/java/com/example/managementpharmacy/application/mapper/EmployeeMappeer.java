package com.example.managementpharmacy.application.mapper;

import com.example.managementpharmacy.application.dto.employee.EmployeeBodyDto;
import com.example.managementpharmacy.application.dto.employee.EmployeeDto;
import com.example.managementpharmacy.application.dto.employee.EmployeeSavedDto;
import com.example.managementpharmacy.application.dto.employee.EmployeeSmallDto;
import com.example.managementpharmacy.persistence.entity.Employee;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMappeer {


    // App ==> client
    EmployeeDto toDto(Employee employee);

    EmployeeSmallDto toSmallDto(Employee employee);

    EmployeeSavedDto toSaveDto(Employee employee);

    // Client ==> app
    @Mapping(source = "roleId", target = "role")
    Employee toEntity(EmployeeBodyDto employeeBodyDto);

    @Mapping(source = "roleId", target = "role")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee updateEntity(@MappingTarget
                          Employee employee,
                          EmployeeBodyDto employeeBodyDto
    );

    RoleEntity mapRoleEntity(Long id);


}
