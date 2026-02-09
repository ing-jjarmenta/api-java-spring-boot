package com.reto.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.reto.backend.dto.account.request.CreateAccountRequest;
import com.reto.backend.dto.account.response.AccountResponse;
import com.reto.backend.exception.BusinessException;
import com.reto.backend.exception.ResourceNotFoundException;
import com.reto.backend.model.Account;
import com.reto.backend.model.AccountStatus;
import com.reto.backend.model.Customer;
import com.reto.backend.repository.AccountRepository;
import com.reto.backend.repository.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer(null, "123456789", "John Doe", "john.doe@example.com");
    }
    
    @Test
    void shouldCreateAccountWhenCustomerExists() {
        
        CreateAccountRequest request = new CreateAccountRequest(1L);        

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.existsByCustomer(customer)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        AccountResponse response = accountService.createAccount(request);
        
        assertNotNull(response);
        assertEquals("ACTIVE", response.getStatus());         
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        CreateAccountRequest request = new CreateAccountRequest(99L);        

        when(customerRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createAccount(request);
        });

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCustomerAlreadyHasAccount() {
        CreateAccountRequest request = new CreateAccountRequest(1L);        

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.existsByCustomer(customer)).thenReturn(true);
        
        assertThrows(BusinessException.class, () -> {
            accountService.createAccount(request);
        });

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldGetAccountByCustomerId() {
        Account account = new Account("ACC123", AccountStatus.ACTIVE, customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomer(customer)).thenReturn(Optional.of(account));

        AccountResponse response = accountService.getAccountByCustomerId(1L);

        assertEquals("ACC123", response.getAccountNumber());
        assertEquals(customer.getId(), response.getCustomerId());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundForCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.findByCustomer(customer)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountByCustomerId(1L);
        });
    }
}
