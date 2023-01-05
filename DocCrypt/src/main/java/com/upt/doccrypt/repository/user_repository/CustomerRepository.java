package com.upt.doccrypt.repository.user_repository;

import com.upt.doccrypt.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByUsername(String username);
    Customer findById(long id);
    Boolean existsByUsername(String username);
}
