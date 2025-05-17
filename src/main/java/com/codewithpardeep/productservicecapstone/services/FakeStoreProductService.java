package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.dtos.FakeStoreReponseDto;
import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreReponseDto fakeStoreReponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreReponseDto.class);
        if (fakeStoreReponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return fakeStoreReponseDto.toProduct();
    }
}
