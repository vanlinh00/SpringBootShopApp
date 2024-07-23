package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.OrderDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Order;
import com.example.springboot_shop_app.models.OrderStatus;
import com.example.springboot_shop_app.models.User;
import com.example.springboot_shop_app.repositories.OrderRepository;
import com.example.springboot_shop_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //return null;
        // Tim xme user id co ton tai khong

        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

        //Convert orderDTO => Order
        // Dung thu vien Model Mapper
        //Tạo một luồng bẳng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        // Cập nhật các trường của đơn hàng từ OrderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());// la thoi diem hien tai
        order.setStatus(OrderStatus.PENDING);

        // Kiem tra shipping date phai >= ngay hom nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException(
                    "Data must be at least today !"
            );
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with it: " + id));

        User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));

        // tao mot luong bang anh xa ring de kiem soat viec anh xa
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(
                        mapper -> mapper.skip(Order::setId)
                );

        // cap nhat cac truong cua don hang tu OrderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public void deleterOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        // no hard-delete, => please soft-delete
        if (order != null) {
            order.setActive(false);
         //   orderRepository.deleteById(id);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
