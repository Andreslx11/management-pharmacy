package com.example.managementpharmacy.application.mapper;


import com.example.managementpharmacy.application.dto.userentity.*;
import com.example.managementpharmacy.persistence.entity.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EmployeeMappeer.class, RoleEntityMapper.class})
public interface UserEntityMapper {

    @Mapping(source = "employee.id", target = "employee") // Mapea el ID del empleado
    @Mapping(source = "role.roleName", target = "role")// Mapea el nombre del rol
    UserDto toDto(UserEntity user);

    @Mapping(source = "employee.id", target = "idEmpleyee") // Mapea el ID correcto del empleado
    @Mapping(expression = "java(user.getEmployee() != null ? user.getEmployee().getFirstName() + ' ' + user.getEmployee().getLastName() : null)",
            target = "fullName")// Genera el nombre completo
    @Mapping(expression = "java(user.getRole() != null ? user.getRole().getRoleName() : null)",
            target = "role")
    UserSmallDto toSmallDto(UserEntity user);

    UserSavedDto toSavedDto(UserEntity user);

    @Mapping(target = "employee", source = "employee.id")
    @Mapping(target = "role", source = "role.roleName")
    UserSecurityDto toSecurityDto(UserEntity user);

    UserEntity toEntity(UserBodyDto userBodyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget
                      UserEntity userEntity,
                      UserBodyDto userBodyDto);

}
