package com.reto.backend.controller;

import jakarta.validation.Valid;
import com.reto.backend.dto.account.request.CreateAccountRequest;
import com.reto.backend.dto.account.response.AccountResponse;
import com.reto.backend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account Controller", description = "Account management")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account for a customer", description = "Creates a new account for a customer")    
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")        
    })
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response = accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get account by customer ID", description = "Retrieves the account associated with a given customer ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid customer ID"),
        @ApiResponse(responseCode = "404", description = "Customer or account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<AccountResponse> getAccountByCustomer(@RequestParam Long customerId) {

        AccountResponse response = accountService.getAccountByCustomerId(customerId);

        return ResponseEntity.ok(response);
    }
}
