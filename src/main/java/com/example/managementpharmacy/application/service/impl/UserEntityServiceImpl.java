package com.example.managementpharmacy.application.service.impl;

import com.example.managementpharmacy.application.dto.userentity.*;
import com.example.managementpharmacy.application.mapper.UserEntityMapper;
import com.example.managementpharmacy.application.service.UserEntityService;
import com.example.managementpharmacy.persistence.entity.Employee;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import com.example.managementpharmacy.persistence.entity.UserEntity;
import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import com.example.managementpharmacy.persistence.repository.EmployeeRepository;
import com.example.managementpharmacy.persistence.repository.RoleEntityRepository;
import com.example.managementpharmacy.persistence.repository.UserEntityRepository;
import com.example.managementpharmacy.shared.exception.AuthenticationUserException;
import com.example.managementpharmacy.shared.exception.BusinessRuleViolationException;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.security.JwtHelper;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


// Lombok annotation to generate constructors and inject IoC beans.
@RequiredArgsConstructor

// Spring Sterotype annotation
@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;


    //**************              Methods CRUD

    @Override
    public List<UserSmallDto> findAll() {
        return userEntityRepository.findAll()
                .stream()
                .map(userEntityMapper::toSmallDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) throws DataNotFoundException {
        return userEntityMapper.toDto(getUserEntityById(id));
    }

    @Transactional
    @Override
    public UserSavedDto create(UserBodyDto userBodyDto) {
        if (userBodyDto.getDocumentNumber() == null || userBodyDto.getEmail() == null || userBodyDto.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Document number, email, and phone number are required.");
        }

        String documentNumber = userBodyDto.getDocumentNumber();
        String email = userBodyDto.getEmail();
        String phoneNumber = userBodyDto.getPhoneNumber();
        Employee empleado = validateUser(documentNumber,
                email, phoneNumber);

        RoleEntity role = employeeRepository.findRoleByEmployeeId(empleado.getId())
                .orElseThrow(() -> new DataNotFoundException("Employee has no role"));

        boolean employeeHasUser = userEntityRepository.existsByEmployeeId(empleado.getId());

        if (employeeHasUser) {
            throw new BusinessRuleViolationException("The employee already has an associated user.");
        }

        UserEntity user = userEntityMapper.toEntity(userBodyDto);
        user.setEnabled(true);
        user.setAccountNoExpired(true);
        user.setAccountNoLocked(true);
        user.setCredentialNoExpired(true);
        user.setCreationDate(LocalDate.now());
        user.setRole(role);
        user.setEmployee(empleado);
        user.setState(State.ENABLED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        empleado.setUserEntity(user);
        employeeRepository.save(empleado);
        return saveAndMapSaveDto(user);
    }

    @Transactional
    @Override
    public UserSavedDto update(Long id, UserBodyDto userBodyDto) throws DataNotFoundException {
        UserEntity user = getUserEntityById(id);
        user.setUpdateDate(LocalDate.now());
        userEntityMapper.updateEntity(user, userBodyDto);
        return saveAndMapSaveDto(user);
    }

    @Transactional
    @Override
    public UserSavedDto disable(Long id) throws DataNotFoundException {
        UserEntity user = getUserEntityById(id);
        user.setState(State.DISABLED);
        return saveAndMapSaveDto(user);
    }


    //******************                 Login

    @Override
    public UserSecurityDto login(AuthDto authDto) throws DataNotFoundException {
        UserEntity user = userEntityRepository.findByUsername(authDto.getUsername())
                .orElseThrow(()
                        -> new DataNotFoundException("User not found with username: " + authDto.getUsername()));
        checkUserSecurityStatus(authDto, user);
        UserSecurityDto userSecurity = userEntityMapper.toSecurityDto(user);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getUsername());
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), List.of(grantedAuthority));

        userSecurity.setTokenAccess(jwtHelper.generateToken(userDetails));

        return userSecurity;
    }

    private UserEntity getUserEntityById(Long id) {
        return userEntityRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found by id: " + id));
    }

    private Employee validateUser(String documentNumber, String email, String phoneNumber) {
        return employeeRepository.findByDetails(documentNumber, email, phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with the given details."));
    }


    private void checkUserSecurityStatus(AuthDto authDto, UserEntity user) {
        if (!passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            throw new AuthenticationUserException("Password invalid");
        }
        if (State.DISABLED.equals(user.getState())) {
            throw new AuthenticationUserException("User is disabled");
        }
        if (!user.isEnabled()) {
            throw new AuthenticationUserException("Account is disabled");
        }
        if (!user.isAccountNoExpired()) {
            throw new AuthenticationUserException("Account is expired");
        }
        if (!user.isAccountNoLocked()) {
            throw new AuthenticationUserException("Account is locked");
        }
        if (!user.isCredentialNoExpired()) {
            throw new AuthenticationUserException("Credential expired");
        }
    }


    //*************            Management methods to administer users

    @Override
    public UserDto assignRoleByHumanResources(Long id, RolePharmacy rolePharmacy) throws DataNotFoundException {
        UserEntity user = getUserEntityById(id);
        RoleEntity role = roleRepository.findByRoleName(rolePharmacy.name())
                .orElseThrow(() -> new DataNotFoundException("Role " + rolePharmacy.name() + " not found"));
        user.setRole(role);
        return userEntityMapper.toDto(userEntityRepository.save(user));
    }


    @Override
    public void managementUser(Long id) throws DataNotFoundException {

    }

    private UserSavedDto saveAndMapSaveDto(UserEntity user) {
        return userEntityMapper.toSavedDto(userEntityRepository.save(user));
    }


    //*************                   Security methods


}
