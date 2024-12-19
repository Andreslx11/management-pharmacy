package com.example.managementpharmacy.persistence.repository;

import com.example.managementpharmacy.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE UPPER(c.state) = UPPER(:state) ORDER BY c.id DESC")
    List<Customer> findByState(@Param("state") String state);
}
