package com.example.springboot_shop_app.repositories;

import com.example.springboot_shop_app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
