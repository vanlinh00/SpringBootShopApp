package com.example.springboot_shop_app.services;
import com.example.springboot_shop_app.dto.OrderDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleterOrder(Long id);

    List<Order> findByUserId(Long userId);
}
