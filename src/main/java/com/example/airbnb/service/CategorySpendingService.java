package com.example.airbnb.service;
import com.example.airbnb.model.Category;
import java.util.Optional;

public interface CategorySpendingService {
    Category save(Category categorySpending);

    Iterable<Category> findAll();

    Optional<Category> findById(Long id);
}
