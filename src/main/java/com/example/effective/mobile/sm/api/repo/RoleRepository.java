package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
