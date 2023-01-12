package com.demo.app.repository;

import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCriteria {

    boolean existsByCustomerEmail(String email);

    boolean existsByCustomerEmailOrCustomerUsername(String email, String username);

    Customer findByCustomerUsername(String username);

    boolean existsByCustomerUsernameAndIsActivatedFalseAndActivationCodeEquals(String username,
                                                                               BigDecimal code);
}
