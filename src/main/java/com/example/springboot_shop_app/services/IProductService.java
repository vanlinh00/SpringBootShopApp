package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.ProductDTO;
import com.example.springboot_shop_app.dto.ProductImageDTO;
import com.example.springboot_shop_app.models.Product;
import com.example.springboot_shop_app.models.ProductImage;
import com.example.springboot_shop_app.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long productId) throws Exception;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String Name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;



}
