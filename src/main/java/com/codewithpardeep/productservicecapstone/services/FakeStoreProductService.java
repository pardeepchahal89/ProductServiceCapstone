package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.dtos.FakeStoreReponseDto;
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
    public Product getProductById(long id) {
        FakeStoreReponseDto fakeStoreReponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreReponseDto.class);

        return fakeStoreReponseDto.toProduct();
    }
}
