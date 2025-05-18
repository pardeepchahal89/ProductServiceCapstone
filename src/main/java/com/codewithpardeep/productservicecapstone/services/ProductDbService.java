package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Category;
import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.repositories.CategoryRespository;
import com.codewithpardeep.productservicecapstone.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductDbService implements ProductService {
    private final CategoryRespository categoryRespository;
    private final ProductRepository productRepository;

    public ProductDbService(CategoryRespository categoryRespository, ProductRepository productRepository) {
        this.categoryRespository = categoryRespository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        buildProduct(product, name, description, price, imageUrl, category);
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        product.setId(id);
        buildProduct(product, name, description, price, imageUrl, category);
        return productRepository.save(product);
    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        return null;
    }

    private Category getCategoryFromDb(String categoryName) {
        Optional<Category> categoryOptional = categoryRespository.findByName(categoryName);
        if(categoryOptional.isPresent()) {
            return categoryOptional.get();
        }
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        return categoryRespository.save(newCategory);
    }

    private void buildProduct(Product product, String name, String description, double price, String imageUrl, String category) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDb(category);
        product.setCategory(categoryObj);
    }
}
