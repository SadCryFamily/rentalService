package com.demo.app.entity;

import com.demo.app.auth.entity.Role;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(name = "uk_mail_username", columnNames = {"customer_username", "email"})
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    private String customerFirstName;

    @Column(name = "last_name")
    private String customerLastName;

    @Column(name = "customer_username")
    private String customerUsername;

    @Column(name = "email")
    private String customerEmail;

    @Column(name = "password")
    private String customerPassword;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_rental",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id"))
    private Set<Rental> rentals = new HashSet<>();

    @Column(name = "activation_code")
    private BigDecimal activationCode;

    @Column(name = "is_activated", columnDefinition = "bool default false")
    private boolean isActivated;

    @CreatedDate
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

}
