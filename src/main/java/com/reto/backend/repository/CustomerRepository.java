package com.reto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reto.backend.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);
}
