package com.demo.app.auth.service;

import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private Long customerId;

    private String customerFirstName;

    private String customerLastName;

    private String customerUsername;

    private String customerEmail;

    private String customerPassword;

    private boolean isActivated;

    private Set<Rental> rentals;

    private BigDecimal activationCode;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long customerId,
                           String customerFirstName, String customerLastName,
                           String customerUsername, String customerEmail,
                           String customerPassword, boolean isActivated,
                           Set<Rental> rentals, BigDecimal activationCode,
                           Collection<? extends GrantedAuthority> authorities) {

        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerUsername = customerUsername;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.isActivated = isActivated;
        this.rentals = rentals;
        this.activationCode = activationCode;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Customer customer) {
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                customer.getCustomerId(),
                customer.getCustomerFirstName(),
                customer.getCustomerLastName(),
                customer.getCustomerUsername(),
                customer.getCustomerEmail(),
                customer.getCustomerPassword(),
                customer.isActivated(),
                customer.getRentals(),
                customer.getActivationCode(),
                authorities);
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public boolean isActivated() {
        return isActivated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.customerPassword;
    }

    @Override
    public String getUsername() {
        return this.customerUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
