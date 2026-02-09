package com.reto.backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.reto.backend.dto.account.request.CreateAccountRequest;
import com.reto.backend.dto.account.response.AccountResponse;
import com.reto.backend.exception.BusinessException;
import com.reto.backend.exception.ResourceNotFoundException;
import com.reto.backend.model.Account;
import com.reto.backend.model.AccountStatus;
import com.reto.backend.model.Customer;
import com.reto.backend.repository.AccountRepository;
import com.reto.backend.repository.CustomerRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public AccountResponse createAccount(CreateAccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> 
            new ResourceNotFoundException("Customer not found")
        );

        if (accountRepository.existsByCustomer(customer)) {
            throw new BusinessException("Customer already has an account");
        }

        Account account = new Account(
            UUID.randomUUID().toString(),
            AccountStatus.ACTIVE,
            customer
        );

        Account savedAccount = accountRepository.save(account);

        return toResponse(savedAccount);
    }

    public AccountResponse getAccountByCustomerId(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> 
            new ResourceNotFoundException("Customer not found")
        );

        Account account = accountRepository.findByCustomer(customer).orElseThrow(() -> 
            new ResourceNotFoundException("Account not found for customer")
        );

        return toResponse(account);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
            account.getId(),
            account.getAccountNumber(),
            account.getStatus().name(),
            account.getCustomer().getId()
        );
    }
}
