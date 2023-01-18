package com.demo.app.annotation;

import com.demo.app.factory.WithCustomMockUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    String customerUsername() default "customer_username";

    String customerPassword() default "customer_password";

}
