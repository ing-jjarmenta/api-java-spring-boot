package com.reto.backend.service;

import com.reto.backend.dto.customer.request.CreateCustomerRequest;
import com.reto.backend.dto.customer.response.CustomerResponse;
import com.reto.backend.model.Customer;
import com.reto.backend.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        
        if (customerRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new IllegalArgumentException("Document number already exists");
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        Customer customer = new Customer(
            request.getDocumentType(),
            request.getDocumentNumber(),
            request.getFullName(),
            request.getEmail()
        );        
        
        return toResponse(customerRepository.save(customer));
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getDocumentType(),
            customer.getDocumentNumber(),
            customer.getFullName(),
            customer.getEmail()
        );
    }
}
