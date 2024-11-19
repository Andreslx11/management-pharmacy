package com.example.managementpharmacy.persistence.entity;

import com.example.managementpharmacy.persistence.enums.entity.Category;
import com.example.managementpharmacy.persistence.enums.entity.Concentration;
import com.example.managementpharmacy.persistence.enums.entity.Presentation;
import com.example.managementpharmacy.shared.state.converter.StateConverter;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


// Lombok
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


// Jpa
@Entity
@Table(name = "products")
public class Product implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private long id;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "generic_name")
    private String genericName;

    private String laboratory;

    @Enumerated(EnumType.STRING)
    private Presentation presentation;

    @Enumerated(EnumType.STRING)
    private Concentration concentration;

    private Integer stock;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "invima_registration")
    private String invimaRegistration;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id_supplier")
    private Supplier supplier;


    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;


    @Convert(converter = StateConverter.class)
    private State state;

    private String description;

    private String contraindications;

    @Column(name= "url_key")
    private String urlkey;
}




