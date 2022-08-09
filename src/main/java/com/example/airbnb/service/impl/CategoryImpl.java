package com.example.airbnb.service.impl;

import com.example.airbnb.model.Category;
import com.example.airbnb.repository.CategoryRepository;
import com.example.airbnb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Iterable<Category> findAllByStatus(int num, Long id) {
        return categoryRepository.findAllByStatus(num, id);
    }

    @Override
    public Iterable<Category> findAllByUserId(Long id) {
        return categoryRepository.findAllByUserId(id);
    }

}
