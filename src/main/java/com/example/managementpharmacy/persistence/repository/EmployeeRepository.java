package com.example.managementpharmacy.persistence.repository;

import com.example.managementpharmacy.persistence.entity.Employee;
import com.example.managementpharmacy.persistence.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE UPPER(e.state) = UPPER(:state) ORDER BY e.id DESC")
    List<Employee> findByState(@Param("state") String state);


    @Query("SELECT e FROM Employee e WHERE e.documentNumber = :documentNumber " +
            "AND e.email = :email AND e.phoneNumber = :phoneNumber AND e.state = 'A'")
    Optional<Employee> findByDetails(@Param("documentNumber") String documentNumber,
                                     @Param("email") String email,
                                     @Param("phoneNumber") String phoneNumber);


    @Query("SELECT e.role FROM Employee e WHERE e.id = :employeeId")
    Optional<RoleEntity> findRoleByEmployeeId(@Param("employeeId") Long employeeId);


    @Query("SELECT e FROM Employee e " +
            "WHERE (:id IS NULL OR e.id = :id) " +
            "AND (:firstName IS NULL OR UPPER(e.firstName) LIKE UPPER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR UPPER(e.lastName) LIKE UPPER(CONCAT('%', :lastName, '%'))) " +
            "AND (:documentNumber IS NULL OR UPPER(e.documentNumber) LIKE UPPER(CONCAT('%', :documentNumber, '%'))) " +
            "AND (:role IS NULL OR UPPER(e.role) LIKE UPPER(CONCAT('%', :role, '%'))) " +
            "AND (:email IS NULL OR UPPER(e.email) LIKE UPPER(CONCAT('%', :email, '%'))) " +
            "AND (:contractStartDate IS NULL OR e.contractStartDate >= :contractStartDate) " +
            "AND (:contractEndDate IS NULL OR e.contractEndDate <= :contractEndDate) " +
            "AND (:state IS NULL OR e.state = :state) " +
            "ORDER BY e.id DESC")
    Page<Employee> paginatedSearch(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("documentNumber") String documentNumber,
            @Param("role") String role,
            @Param("email") String email,
            @Param("contractStartDate") LocalDate contractStartDate,
            @Param("contractEndDate") LocalDate contractEndDate,
            @Param("state") String state,
            Pageable pageable);


}
