package com.reto.backend.controller;

import com.reto.backend.dto.customer.request.CreateCustomerRequest;
import com.reto.backend.dto.customer.response.CustomerResponse;
import com.reto.backend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Controller", description = "Customer management")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided information")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Customer created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
