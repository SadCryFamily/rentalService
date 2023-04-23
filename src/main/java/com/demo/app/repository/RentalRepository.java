package com.demo.app.repository;

import com.demo.app.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    boolean existsByRentalCityAndRentalAddress(String city, String address);

    Optional<Rental> findByRentalIdAndIsDeletedFalse(Long id);

    List<Rental> findAllByIsDeletedFalse();

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE rental SET is_deleted = true WHERE rental_id IN (:id)", nativeQuery = true)
    int updateIsDeletedByRentalId(@Param("id") Long rentalId);

    @Query(value = "SELECT cr.customer_id, cr.rental_id, r.is_deleted FROM customer_rental AS cr " +
            "INNER JOIN customer AS c ON c.customer_id = cr.customer_id " +
            "INNER JOIN rental AS r ON r.rental_id = cr.rental_id " +
            "WHERE cr.customer_id IN (:cid) AND r.rental_id IN (:rid)", nativeQuery = true)
    Optional<Rental> existsByCustomerIdAndRentalId(@Param("cid") Long cid, @Param("rid") Long rid);

    @Query(value = "SELECT r.rental_id, r.rental_photo, r.rental_name, r.rental_city, r.rental_address,r.rental_description, r.rental_area, r.rental_price, r.rental_created_at, r.rental_updated_at, r.is_deleted " +
            "FROM rental AS r " +
            "INNER JOIN customer_rental AS cr ON cr.rental_id = r.rental_id " +
            "INNER JOIN customer AS c ON c.customer_id = cr.customer_id " +
            "WHERE c.customer_id IN(:cid) AND r.is_deleted IN (false)", nativeQuery = true)
    Set<Rental> getAllRentalsByIsDeletedFalse(@Param("cid") Long cid);

}
