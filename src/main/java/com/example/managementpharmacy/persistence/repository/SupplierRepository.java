package com.example.managementpharmacy.persistence.repository;


import com.example.managementpharmacy.persistence.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // JPQL query
    @Query("SELECT s FROM  Supplier s " +
            "WHERE UPPER(s.state) = UPPER(:state)  ORDER BY s.id DESC")
    List<Supplier> findByState(String state);


    // Derived query
    List<Supplier> findByCompanyNameContainingIgnoreCase(String name);


    @Query("SELECT s  FROM Supplier s " +
            "WHERE( :companyName IS NULL  OR UPPER(s.companyName)  " +
            "LIKE UPPER(CONCAT( '%', :companyName, '%')))   " +
            "AND (:state IS NULL OR UPPER(s.state) = UPPER(:state)) ")
    List<Supplier> findAllByFilters(String companyName, String state);


    @Query(value = "SELECT s.id_supplier, s.company_name, s.contact, s.phone, s.email, s.nit, " +
            "s.creation_date, s.update_date, s.state, s.url_key " +
            "FROM suppliers s " +
            "WHERE (:companyName IS NULL OR UPPER(s.company_name) LIKE UPPER(CONCAT('%', :companyName, '%'))) " +
            "AND   (:contact IS NULL OR UPPER(s.contact) LIKE UPPER(CONCAT('%', :contact , '%'))) " +
            "AND   (:phone IS NULL OR UPPER(s.phone) LIKE UPPER(CONCAT('%', :phone, '%'))) " +
            "AND   (:email  IS NULL OR UPPER(s.email) LIKE UPPER(CONCAT('%', :email, '%'))) " +
            "AND   (:nit IS NULL OR UPPER(s.nit)  LIKE UPPER(CONCAT('%', :nit, '%'))) " +
            "AND   (:state IS NULL OR UPPER(s.state)  = UPPER(:state)) " +
            "AND   (:creationDateFrom  IS NULL OR s.creation_date >= :creationDateFrom) " +
            "AND   (:creationDateTo IS NULL OR s.creation_date <= :creationDateTo)",
            nativeQuery = true)
    Page<Supplier> paginatedSearch(
            @Param("companyName") String companyName,
            @Param("contact") String contact,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("nit") String nit,
            @Param("state") String state,
            @Param("creationDateFrom") LocalDate creationDateFrom,
            @Param("creationDateTo") LocalDate creationDateTo,
            Pageable pageable);


}
