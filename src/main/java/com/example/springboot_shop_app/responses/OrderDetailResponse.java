package com.example.springboot_shop_app.responses;

import com.example.springboot_shop_app.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetailResponse {

    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;
    private String color;

    public static OrderDetailResponse fromOrderDetail(
            OrderDetail orderDetail
    ) {
        return OrderDetailResponse
                .builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }

}
