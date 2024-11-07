package com.example.managementpharmacy.persistence.entity;


import com.example.managementpharmacy.shared.state.converter.StateConverter;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// Jpa
@Entity
@Table(name = "suppliers")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    private String contact;
    private String phone;
    private String email;
    private String nit;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;


    @Convert(converter = StateConverter.class)
    private State state;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();
}

