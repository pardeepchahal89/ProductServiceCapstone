package com.codewithpardeep.productservicecapstone.controllers;

import com.codewithpardeep.productservicecapstone.dtos.ErrorDto;
import com.codewithpardeep.productservicecapstone.dtos.ProductResponseDto;
import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProduct(@PathVariable int id) {
        Product product = this.productService.getProductById(id);
        return ProductResponseDto.from(product);
    }


    @ExceptionHandler(NullPointerException.class)
    public ErrorDto handleNullPointerException() {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage("Product cannot be null");

        return errorDto;
    }

}
