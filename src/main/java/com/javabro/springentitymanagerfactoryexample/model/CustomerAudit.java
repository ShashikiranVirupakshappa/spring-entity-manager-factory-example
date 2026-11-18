package com.javabro.springentitymanagerfactoryexample.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "customer_audit")
public class CustomerAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    private String status;
    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
