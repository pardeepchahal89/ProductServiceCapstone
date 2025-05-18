package com.codewithpardeep.productservicecapstone.repositories;

import com.codewithpardeep.productservicecapstone.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRespository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Category save(Category category);
}
