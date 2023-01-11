package com.demo.app.auth.service;

import com.demo.app.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private Long customerId;

    private String customerFirstName;

    private String customerLastName;

    private String customerUsername;

    private String customerEmail;

    @JsonIgnore
    private String customerPassword;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long customerId, String customerFirstName,
                           String customerLastName, String customerUsername,
                           String customerEmail, String customerPassword,
                           Collection<? extends GrantedAuthority> authorities) {

        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerUsername = customerUsername;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
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
                authorities);
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
