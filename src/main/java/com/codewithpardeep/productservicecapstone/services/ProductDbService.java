package com.codewithpardeep.productservicecapstone.services;

import com.codewithpardeep.productservicecapstone.exceptions.ProductNotFoundException;
import com.codewithpardeep.productservicecapstone.models.Category;
import com.codewithpardeep.productservicecapstone.models.Product;
import com.codewithpardeep.productservicecapstone.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductDbService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        // Optional<Category> categoryOptional = categoryRepository.findByName("electronics");
        // List<Product> products = productRepository.findByCategory(categoryOptional.get());

        //List<Product> products = productRepository.findByCategory_Name("electronics");

        // List<Product> products = productRepository.getProductsByCategoryNameNative("electronics");

        // List<ProductProjection> projections = productRepository.getProjectedProduct("electronics");

        // List<ProductProjectionDto> projectionDtos = productRepository.getProjectedProductDto("electronics");
        // System.out.println(projectionDtos);

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
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        }
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        return categoryRepository.save(newCategory);
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
