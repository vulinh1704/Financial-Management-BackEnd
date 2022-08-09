package com.example.airbnb.service;
import com.example.airbnb.model.Category;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface CategoryService {
    Category save(Category category);

    Iterable<Category> findAll();

    Optional<Category> findById(Long id);

    void delete(Long id);

    Iterable<Category> findAllByStatus(int num, Long id);

    Iterable<Category> findAllByUserId(Long id);
}
