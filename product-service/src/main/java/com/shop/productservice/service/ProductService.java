package com.shop.productservice.service;

import com.shop.productservice.dto.ProductRequest;
import com.shop.productservice.dto.ProductResponse;
import com.shop.productservice.model.Product;
import com.shop.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepo;
    public void createProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        var p = productRepo.save(product);
        log.info("product "+p.getName() +" with id: "+p.getId() +" is saved!" );

    }

    public List<ProductResponse> getAllProduct() {
        List<Product> product = productRepo.findAll();

        return product.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .build();
    }
}
