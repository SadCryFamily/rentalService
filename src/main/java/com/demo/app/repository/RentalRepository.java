package com.demo.app.repository;

import com.demo.app.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    boolean existsByRentalCityAndRentalAddress(String city, String address);

}
