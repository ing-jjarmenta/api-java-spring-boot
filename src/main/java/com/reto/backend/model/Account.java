package com.reto.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "account_number"),
    @UniqueConstraint(columnNames = "customer_id")
})
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected Account() {}

    public Account(String accountNumber, AccountStatus status, Customer customer) {
        this.accountNumber = accountNumber;
        this.status = status;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }
}
