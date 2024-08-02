package com.security.Learning.repo;

import com.security.Learning.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
    Role findByName(String name);
}
