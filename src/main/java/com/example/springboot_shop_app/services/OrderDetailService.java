package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.OrderDTO;
import com.example.springboot_shop_app.dto.OrderDetailDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Order;
import com.example.springboot_shop_app.models.OrderDetail;
import com.example.springboot_shop_app.models.Product;
import com.example.springboot_shop_app.models.User;
import com.example.springboot_shop_app.repositories.OrderDetailRepository;
import com.example.springboot_shop_app.repositories.OrderRepository;
import com.example.springboot_shop_app.repositories.ProductRepository;
import com.example.springboot_shop_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {

        // Tim xem orderID co ton tai khong
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Order with id: " + orderDetailDTO.getOrderId()
                        ));
        // tim product theo id

        Product product =
                productRepository.findById(
                        orderDetailDTO.getProductId()
                ).orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Canot find OrderDetail with id: " + id)
                );
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData) throws DataNotFoundException {

        // tim xo Order detail co ton tai ko da
        OrderDetail existingOrderDeatail = orderDetailRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find order detail with id: " + id)
                );
        Order existingOrder = orderRepository.findById(newOrderDetailData.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        Product existingProduct = productRepository.findById(newOrderDetailData.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + newOrderDetailData.getProductId()));

        existingOrderDeatail.setPrice(newOrderDetailData.getPrice());
        existingOrderDeatail.setNumberOfProducts(newOrderDetailData.getNumberOfProducts());
        existingOrderDeatail.setTotalMoney(newOrderDetailData.getTotalMoney());
        existingOrderDeatail.setColor(newOrderDetailData.getColor());
        existingOrderDeatail.setOrder(existingOrder);
        existingOrderDeatail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDeatail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long OrderId) {
        return orderDetailRepository.findByOrderId((OrderId));
    }
}
