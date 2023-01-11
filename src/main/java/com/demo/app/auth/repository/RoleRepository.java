package com.demo.app.auth.repository;

import com.demo.app.auth.entity.CustomerRoles;
import com.demo.app.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(CustomerRoles role);
}
