package com.codewithpardeep.productservicecapstone.repositories;

import com.codewithpardeep.productservicecapstone.dtos.ProductProjection;
import com.codewithpardeep.productservicecapstone.dtos.ProductProjectionDto;
import com.codewithpardeep.productservicecapstone.models.Category;
import com.codewithpardeep.productservicecapstone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll();

    List<Product> findByCategory(Category category);

    List<Product> findByCategory_Name(String name);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = CustomQuery.GET_PRODUCT_FROM_CATEGORY_NAME, nativeQuery = true)
    List<Product> getProductsByCategoryNameNative(@Param("categoryName") String categoryName);

    @Query("select p.name, p.price  from Product p where p.category.name = :categoryName")
    List<ProductProjection> getProjectedProduct(@Param("categoryName") String categoryName);

    @Query("select new com.codewithpardeep.productservicecapstone.dtos.ProductProjectionDto(p.name, p.price)  from Product p where p.category.name = :categoryName")
    List<ProductProjectionDto> getProjectedProductDto(@Param("categoryName") String categoryName);


}
