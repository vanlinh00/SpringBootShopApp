package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.OrderDetailDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {

    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail)
            throws Exception;

    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;

    void deleteById(Long id);

    List<OrderDetail> findByOrderId(Long OrderId);


}
