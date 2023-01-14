package com.demo.app.repository;

import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Repository
public class CustomerRepositoryCriteriaImpl implements CustomerRepositoryCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean updateIsCustomerActivatedByUsername(String username) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Customer> update = cb.createCriteriaUpdate(Customer.class);

        Root<Customer> root = update.from(Customer.class);

        update
                .set(root.get("isActivated"), true)
                .where(cb.equal(root.get("customerUsername"), username));

        entityManager.createQuery(update).executeUpdate();

        return true;
    }

    @Override
    public boolean updateIsCustomerDeletedByUsername(String username) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Customer> update = cb.createCriteriaUpdate(Customer.class);

        Root<Customer> root = update.from(Customer.class);

        update
                .set(root.get("isDeleted"), true)
                .where(cb.equal(root.get("customerUsername"), username));

        entityManager.createQuery(update).executeUpdate();

        return true;
    }
}
