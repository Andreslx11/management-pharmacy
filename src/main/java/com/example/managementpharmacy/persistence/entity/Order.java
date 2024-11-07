package com.example.managementpharmacy.persistence.entity;

import com.example.managementpharmacy.persistence.enums.entity.OrderStatus;
import com.example.managementpharmacy.persistence.enums.entity.PaymentMethod;
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
import java.time.LocalDateTime;


// Lombok
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


// Jpa
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @Column(name = "order_date")
    private String orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id_customer", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id_employee", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    //    @Enumerated(EnumType.STRING)
    @Convert(converter = StateConverter.class)
    private State state;
}
