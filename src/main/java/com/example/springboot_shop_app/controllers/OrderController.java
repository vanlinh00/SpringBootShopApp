package com.example.springboot_shop_app.controllers;

import com.example.springboot_shop_app.dto.OrderDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Order;
import com.example.springboot_shop_app.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> erroreMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
            }

            Order orderReponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderReponse);
            //return ResponseEntity.ok("createOrder succesfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(
            @Valid @PathVariable("user_id")
            Long userId) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
            //return ResponseEntity.ok("Lấy ra danh sách order từ user_id " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        //    return ResponseEntity.ok("Cập nhật thông tin 1 order");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @Valid
            @PathVariable Long id) {
        orderService.deleterOrder(id);
        return ResponseEntity.ok("Order deleted succesfully");
        // return ResponseEntity.ok("Order Deleted SuccesFully");
    }
}
