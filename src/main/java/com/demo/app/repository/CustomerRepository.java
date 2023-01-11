package com.demo.app.repository;

import com.demo.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCustomerEmail(String email);

    boolean existsByCustomerEmailOrCustomerUsername(String email, String username);

    Customer findByCustomerUsername(String username);

}
