package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.ProductDTO;
import com.example.springboot_shop_app.dto.ProductImageDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Category;
import com.example.springboot_shop_app.models.Product;
import com.example.springboot_shop_app.models.ProductImage;
import com.example.springboot_shop_app.repositories.CategoryRepository;
import com.example.springboot_shop_app.repositories.ProductImageRepository;
import com.example.springboot_shop_app.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {

        // khiểm tra xem tồi tại category id ko

        Category exitstingCategory =
                categoryRepository.
                        findById(productDTO.getCategoryId())
                        .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Cannot find category with id :" + productDTO.getCategoryId())
                        );

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(exitstingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id=" + productId)
                );
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // lay danh sach san pham theo trang(Page) va gioi han(limit)
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(long id,
                                 ProductDTO productDTO)
            throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            //coopy cac thuoc tinh tu DTO->Product
            // co the su dung ModelMapper

            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " +
                                            productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getName());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String Name) {
        return productRepository.existsByName(Name);
    }

    @Override
    public ProductImage createProductImage(Long productId,
                                           ProductImageDTO productImageDTO)
            throws Exception {
        Product existingProduct = productRepository
                .findById(productImageDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+productImageDTO.getProductId()));

        ProductImage newProductImage= ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

    // ko cho insert qua 5 anh cho 1 san pham
        int size=productImageRepository.findByProductId(productId).size();
        if(size>=5){
            throw new InvalidParameterException("Number of images must be <=5");

        }
        return productImageRepository.save(newProductImage);
    }
}
