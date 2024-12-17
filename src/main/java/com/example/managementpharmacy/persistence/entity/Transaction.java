package com.example.managementpharmacy.persistence.entity;

import com.example.managementpharmacy.persistence.enums.entity.TransactionType;
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

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private String transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id_invoice", nullable = false)
    private Invoice invoice;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id_employee", nullable = false)
    private Employee employee;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Convert(converter = StateConverter.class)
    private State state;
}
