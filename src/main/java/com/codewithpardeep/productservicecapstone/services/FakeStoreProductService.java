package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.dtos.FakeStoreResponseDto;
import com.codewithpardeep.productservicecapstone.dtos.FakeStoreRequestDto;
import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreResponseDto.class);
        if (fakeStoreResponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return fakeStoreResponseDto.toProduct();
    }


    @Override
    public List<Product> getAllProducts() {
        FakeStoreResponseDto[] fakeStoreResponseDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreResponseDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos) {
            Product product = fakeStoreResponseDto.toProduct();
            products.add(product);
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto fakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreRequestDto,
                FakeStoreResponseDto.class
        );
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto updateFakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> httpEntity = new HttpEntity<>(updateFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreResponseDto>  responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                httpEntity,
                FakeStoreResponseDto.class);

        return responseEntity.getBody().toProduct();
    }

    private FakeStoreRequestDto createDtoFromParams(String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setImage(imageUrl);
        fakeStoreRequestDto.setCategory(category);
        return fakeStoreRequestDto;
    }

}
