package com.reto.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.reto.backend.dto.customer.request.CreateCustomerRequest;
import com.reto.backend.dto.customer.response.CustomerResponse;
import com.reto.backend.exception.BusinessException;
import com.reto.backend.model.Customer;
import com.reto.backend.model.DocumentType;
import com.reto.backend.repository.CustomerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer(DocumentType.CC, "123456789", "John Doe", "jhon@doe.com");
    }
    
    @Test
    void shouldCreateCustomerSuccessfully() {

        CreateCustomerRequest request = new CreateCustomerRequest(DocumentType.CC, "123456789", "John Doe", "jhon@doe.com");

        when(customerRepository.existsByDocumentNumber("123456789")).thenReturn(false);
        when(customerRepository.existsByEmail("jhon@doe.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponse response = customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals(customer.getDocumentNumber(), response.getDocumentNumber());
        assertEquals(customer.getFullName(), response.getFullName());
        assertEquals(customer.getEmail(), response.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenDocumentNumberExists() {

        CreateCustomerRequest request = new CreateCustomerRequest(DocumentType.CC, "123456789", "John Doe", "jhon@doe.com");

        when(customerRepository.existsByDocumentNumber("123456789")).thenReturn(true);

        assertThrows(BusinessException.class, () -> {
            customerService.createCustomer(request);
        });
        
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {

        CreateCustomerRequest request = new CreateCustomerRequest(DocumentType.CC, "123456789", "John Doe", "jhon@doe.com");

        when(customerRepository.existsByDocumentNumber("123456789")).thenReturn(false);
        when(customerRepository.existsByEmail("jhon@doe.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> {
            customerService.createCustomer(request);
        });
        
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldGetAllCustomers() {

        when(customerRepository.findAll()).thenReturn(List.of(customer));
    
        List<CustomerResponse> responses = customerService.getAllCustomers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(customer.getDocumentNumber(), responses.get(0).getDocumentNumber());
        assertEquals(customer.getFullName(), responses.get(0).getFullName());
        assertEquals(customer.getEmail(), responses.get(0).getEmail());
    }
}
