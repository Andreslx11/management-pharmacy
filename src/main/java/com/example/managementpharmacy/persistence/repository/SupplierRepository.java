package com.example.managementpharmacy.persistence.repository;

import com.example.managementpharmacy.persistence.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long > {
}
