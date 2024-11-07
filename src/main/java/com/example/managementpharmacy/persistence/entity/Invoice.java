package com.example.managementpharmacy.persistence.entity;


import com.example.managementpharmacy.persistence.enums.entity.InvoiceStatus;
import com.example.managementpharmacy.persistence.enums.entity.PaymentMethod;
import com.example.managementpharmacy.shared.state.converter.StateConverter;
import com.example.managementpharmacy.shared.state.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Lombok
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long id;

    @Column(name = "invoice_date", nullable = false)
    private String invoiceDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id_customer", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id_order", nullable = false)
    private Order order;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status", nullable = false)
    private InvoiceStatus invoiceStatus;

    @Column(name = "authorization_number", nullable = false)
    private String authorizationNumber;

    @Column(name = "authorization_date", nullable = false)
    private String authorizationDate;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

//    @Enumerated(EnumType.STRING)
    @Convert(converter = StateConverter.class)
    private State state;
}
