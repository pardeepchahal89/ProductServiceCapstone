package com.codewithpardeep.productservicecapstone.controllers;

import com.codewithpardeep.productservicecapstone.dtos.CreateFakeStoreProductRequestDto;
import com.codewithpardeep.productservicecapstone.dtos.ProductResponseDto;
import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) {
        Product product = this.productService.createProduct(
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );
        return ProductResponseDto.from(product);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto replaceProduct(@PathVariable("id") long id,
                                             @RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) {
        Product product = this.productService.replaceProduct(
                id,
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );
        return ProductResponseDto.from(product);
    }

    @PatchMapping(
            path = "/products/{id}",
            consumes = "application/json-patch+json"
    )
    public ProductResponseDto updateProduct(
            @PathVariable("id") long id,
            @RequestBody JsonPatch jsonPatch
    ) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        Product product = this.productService.applyPatchToProduct(id, jsonPatch);
        return ProductResponseDto.from(product);
    }
}
