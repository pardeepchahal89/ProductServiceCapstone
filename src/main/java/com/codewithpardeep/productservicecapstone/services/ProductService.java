package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
}
