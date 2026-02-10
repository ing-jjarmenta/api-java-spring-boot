package com.reto.backend.dto.account.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {
    
    @NotNull(message = "customerId is required")
    private Long customerId;

    @JsonCreator
    public CreateAccountRequest(@JsonProperty("customerId") Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
