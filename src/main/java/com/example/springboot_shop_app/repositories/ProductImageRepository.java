package com.example.springboot_shop_app.repositories;

import com.example.springboot_shop_app.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
List<ProductImage> findByProductId(Long productId);
}
