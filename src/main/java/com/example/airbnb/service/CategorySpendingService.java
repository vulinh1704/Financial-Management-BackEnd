package com.example.airbnb.service;
import com.example.airbnb.model.CategorySpending;
import java.util.Optional;

public interface CategorySpendingService {
    CategorySpending save(CategorySpending categorySpending);

    Iterable<CategorySpending> findAll();

    Optional<CategorySpending> findById(Long id);
}
