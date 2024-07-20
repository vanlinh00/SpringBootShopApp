package com.example.springboot_shop_app.repositories;

import com.example.springboot_shop_app.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //Tìm các đơn hàng của 1 user nào đó
    List<Order> findByUserId(Long UserId);
}
