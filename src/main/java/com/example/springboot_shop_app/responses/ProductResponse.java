package com.example.springboot_shop_app.responses;
import com.example.springboot_shop_app.models.Category;
import com.example.springboot_shop_app.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse {

    private String name;
    private Float price;
    private String thumbnail;
    private String description;

  //  @ManyToOne
  //  @JoinColumn(name="category_id")
    private Category category;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse =
                ProductResponse.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .thumbnail(product.getThumbnail())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .build();
        productResponse.setCreateAt(product.getCreatedAt());
        productResponse.setUpdateAt(product.getUpdateAt());
        return productResponse;
    }
}
