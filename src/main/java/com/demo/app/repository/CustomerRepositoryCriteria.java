package com.demo.app.repository;

import org.springframework.data.jpa.repository.Modifying;

public interface CustomerRepositoryCriteria {

    @Modifying(flushAutomatically = true)
    boolean updateIsCustomerActivatedByUsername(String username);

}
