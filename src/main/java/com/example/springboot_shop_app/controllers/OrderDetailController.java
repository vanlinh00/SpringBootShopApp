package com.example.springboot_shop_app.controllers;

import com.example.springboot_shop_app.dto.OrderDetailDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Order;
import com.example.springboot_shop_app.models.OrderDetail;
import com.example.springboot_shop_app.responses.OrderDetailResponse;
import com.example.springboot_shop_app.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${api.prefix}/order_details")
@Controller
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    // Thêm mới 1 order detail
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetail) {
        try {
            OrderDetail newOrdarDetail = orderDetailService.createOrderDetail(newOrderDetail);
            return ResponseEntity.ok().body(
                    OrderDetailResponse.fromOrderDetail(newOrdarDetail)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //return ResponseEntity.ok("createOrderDetail here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id)
            throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

    //lấy ra danh sách các order_details của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        // return ResponseEntity.ok("getOrderDetails with orderId = " + orderId);
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
        //return ResponseEntity.ok("getOrderDetail with id = " + id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailData) {
//        return ResponseEntity.ok("updateOrderDetail with id=" + id
//                + ",newOrderDetailData: " + newOrderDetailData);
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, newOrderDetailData);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable("id") Long id) {
        // return ResponseEntity.noContent().build();
        orderDetailService.deleteById(id);
        return ResponseEntity.ok().body("Delete Order detail with id : " + id + " successfully");
    }

}
