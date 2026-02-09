package com.reto.backend.dto.account.request;

import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {
    
    @NotNull(message = "customerId is required")
    private Long customerId;

    public CreateAccountRequest(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
