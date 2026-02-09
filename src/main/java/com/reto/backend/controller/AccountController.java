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

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response = accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<AccountResponse> getAccountByCustomer(@RequestParam Long customerId) {

        AccountResponse response = accountService.getAccountByCustomerId(customerId);

        return ResponseEntity.ok(response);
    }
}
