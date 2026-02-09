package com.reto.backend.repository;

import java.util.Optional;
import com.reto.backend.model.Account;
import com.reto.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByCustomer(Customer customer);
    
    Optional<Account> findByCustomer(Customer customer);
}
