package com.reto.backend.dto.account.response;

public class AccountResponse {
    
    private Long id;
    private String accountNumber;
    private String status;
    private Long customerId;

    public AccountResponse(Long id, String accountNumber, String status, Long customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.status = status;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
