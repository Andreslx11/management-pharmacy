package com.example.managementpharmacy.persistence.entity;


import com.example.managementpharmacy.persistence.enums.entity.RolePharmacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//JPA
@Entity
@Table(name = "user_roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false)
    private RolePharmacy roleName;

    private String description;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

}
