package com.demo.app.repository;

import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCriteria {

    boolean existsByCustomerEmailOrCustomerUsername(String email, String username);

    Customer findByCustomerUsername(String username);

    Customer findByCustomerEmail(String email);

    boolean existsByCustomerEmailAndIsActivatedFalse(String email);

    boolean existsByCustomerUsernameAndIsActivatedFalse(String username);


    boolean existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(String username);

}
