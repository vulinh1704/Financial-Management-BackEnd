package com.example.airbnb.service;
import com.example.airbnb.model.Category;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);

    Iterable<Category> findAll();

    Optional<Category> findById(Long id);

    void remove(Long id);
}
