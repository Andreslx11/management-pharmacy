package com.example.managementpharmacy.persistence.entity;


import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import com.example.managementpharmacy.shared.state.converter.StateConverter;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

//Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//JPA
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private RolePharmacy rolePharmacy;


    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    //    @Enumerated(EnumType.STRING)
    @Convert(converter = StateConverter.class)
    private State state;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;
}

