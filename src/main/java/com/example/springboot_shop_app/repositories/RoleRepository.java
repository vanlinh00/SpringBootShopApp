package com.example.springboot_shop_app.repositories;

import com.example.springboot_shop_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
