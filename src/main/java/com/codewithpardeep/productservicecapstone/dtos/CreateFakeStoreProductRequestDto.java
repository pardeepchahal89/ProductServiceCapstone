package com.codewithpardeep.productservicecapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreProductRequestDto {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

}
