package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
}
