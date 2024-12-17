package com.example.managementpharmacy.persistence.repository;

import com.example.managementpharmacy.persistence.entity.RoleEntity;
import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleName(String name);

    Optional<RoleEntity> findByRoleName(RolePharmacy roleName);


}
