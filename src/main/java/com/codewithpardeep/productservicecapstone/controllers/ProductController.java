package com.codewithpardeep.productservicecapstone.controllers;

import com.codewithpardeep.productservicecapstone.dtos.ProductResponseDto;
import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProduct(@PathVariable int id) throws ProductNotFoundException {
        Product product = this.productService.getProductById(id);
        return ProductResponseDto.from(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = this.productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        // return products.stream().map(ProductResponseDto::from).collect(Collectors.toList());
        for (Product product : products) {
            productResponseDtos.add(ProductResponseDto.from(product));
        }
        return productResponseDtos;
    }

}
