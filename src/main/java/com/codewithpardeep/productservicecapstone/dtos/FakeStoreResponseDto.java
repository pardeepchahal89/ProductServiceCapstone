package com.codewithpardeep.productservicecapstone.dtos;

import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreResponseDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setName(title);
        product.setDescription(description);
        product.setImageUrl(image);
        product.setPrice(price);
        Category productCategory = new Category();
        productCategory.setName(category);

        product.setCategory(productCategory);
        return product;
    }
}
