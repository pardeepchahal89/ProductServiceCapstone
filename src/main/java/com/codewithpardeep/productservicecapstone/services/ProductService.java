package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();

    Product createProduct(String name, String description, double price, String imageUrl, String category);
    Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category);
}
