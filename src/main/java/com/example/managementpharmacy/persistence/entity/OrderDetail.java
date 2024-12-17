package com.example.managementpharmacy.persistence.entity;


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
@Table(name = "order_details")
public class OrderDetail  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id_order", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id_product", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Convert(converter = StateConverter.class)
    private State state;
}