package com.demo.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "rental", uniqueConstraints = {
        @UniqueConstraint(name = "uk_city_address", columnNames = {"rental_city", "rental_address"})
})
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long rentalId;

    @Column(name = "rental_name")
    private String rentalName;

    @Column(name = "rental_city", unique = true)
    private String rentalCity;

    @Column(name = "rental_address", unique = true)
    private String rentalAddress;

    @Column(name = "rental_description")
    private String rentalDescription;

    @Column(name = "rental_area")
    private int rentalArea;

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "rental_created_at")
    private Date rentalCreatedAt;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name = "rental_updated_at")
    private Date rentalUpdatedAt;

    @Column(name = "is_deleted", columnDefinition = "bool default false")
    private boolean isDeleted;
}
