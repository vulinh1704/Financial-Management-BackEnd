package com.example.airbnb.service.impl;

import com.example.airbnb.model.CategorySpending;
import com.example.airbnb.repository.CategorySpendingRepository;
import com.example.airbnb.service.CategorySpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategorySpendingImpl implements CategorySpendingService {
    @Autowired
    private CategorySpendingRepository categorySpendingRepository;

    @Override
    public CategorySpending save(CategorySpending categorySpending) {
        return categorySpendingRepository.save(categorySpending);
    }

    @Override
    public Iterable<CategorySpending> findAll() {
        return categorySpendingRepository.findAll();
    }

    @Override
    public Optional<CategorySpending> findById(Long id) {
        return categorySpendingRepository.findById(id);
    }

}
