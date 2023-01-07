package com.demo.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    private String customerFirstName;

    @Column(name = "last_name")
    private String customerLastName;

}
