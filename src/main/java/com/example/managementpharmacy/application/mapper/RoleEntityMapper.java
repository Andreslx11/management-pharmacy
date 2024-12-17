package com.example.managementpharmacy.application.mapper;

import com.example.managementpharmacy.application.dto.roleentity.RoleBodyDto;
import com.example.managementpharmacy.application.dto.roleentity.RoleDto;
import com.example.managementpharmacy.application.dto.roleentity.RoleSavedDto;
import com.example.managementpharmacy.application.dto.roleentity.RoleSmallDto;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleEntityMapper {

    // App ==> client
    RoleDto toDto(RoleEntity roleEntity);

    RoleSmallDto toSmallDto(RoleEntity roleEntity);

    RoleSavedDto toSaveDto(RoleEntity roleEntity);

    // Client ==> app
    RoleEntity toEntity(RoleBodyDto roleBodyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity updateEntity(@MappingTarget
                            RoleEntity roleEntity,
                            RoleBodyDto roleBodyDto
    );


}
