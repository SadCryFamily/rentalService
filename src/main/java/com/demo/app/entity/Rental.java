package com.demo.app.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long rentalId;

    @Column(name = "rental_name")
    private String rentalName;

    @Column(name = "rental_city")
    private String rentalCity;

    @Column(name = "rental_address")
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
}
