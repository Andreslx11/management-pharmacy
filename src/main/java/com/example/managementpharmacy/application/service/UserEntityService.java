package com.example.managementpharmacy.application.service;

import com.example.managementpharmacy.application.dto.userentity.*;
import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;

import java.util.List;

public interface UserEntityService {
    // CRUD
    List<UserSmallDto> findAll();

    UserDto findById(Long id) throws DataNotFoundException;

    UserSavedDto create(UserBodyDto userBodyDto);

    UserSavedDto update(Long id, UserBodyDto userBodyDto) throws DataNotFoundException;

    UserSavedDto disable(Long id) throws DataNotFoundException;

    UserDto assignRoleByHumanResources(Long id, RolePharmacy rolePharmacy) throws DataNotFoundException;

    // login
    UserSecurityDto login(AuthDto authDto) throws DataNotFoundException;

    void managementUser(Long id) throws DataNotFoundException;
}
