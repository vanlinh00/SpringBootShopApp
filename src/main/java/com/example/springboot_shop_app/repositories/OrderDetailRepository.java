package com.example.springboot_shop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot_shop_app.models.*;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findByOrderId(Long orderId);
}
