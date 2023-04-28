package com.demo.app.factory;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.service.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class WithCustomMockUserSecurityContextFactory
        implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser withCustomMockUser) {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        UserDetails principal = new UserDetailsImpl(1L,
                "firstName",
                "lastName",
                withCustomMockUser.customerUsername(),
                "email",
                withCustomMockUser.customerPassword(),
                true,
                false,
                Collections.emptySet(),
                Set.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, Set.of(new SimpleGrantedAuthority("ROLE_USER")));

        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
