package com.demo.app.auth.entity;

public enum CustomerRoles {

    ROLE_USER("USER"),
    ROLE_MODERATOR("MODERATOR"),
    ROLE_ADMIN("ADMIN");

    private String roleProperty;

    CustomerRoles(String roleProperty) {
        this.roleProperty = roleProperty;
    }

    public String extractRoleProperty() {
        return this.roleProperty;
    }



}
