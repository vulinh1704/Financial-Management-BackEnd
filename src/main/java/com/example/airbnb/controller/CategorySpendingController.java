package com.example.airbnb.controller;

import com.example.airbnb.model.CategorySpending;
import com.example.airbnb.service.CategorySpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("categories-spending")
public class CategorySpendingController {
    @Autowired
    private CategorySpendingService categorySpendingService;

    @GetMapping
    public ResponseEntity<Iterable<CategorySpending>> findAll() {
        return new ResponseEntity<>(categorySpendingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategorySpending> findById(@PathVariable Long id) {
        Optional<CategorySpending> categorySpendingOptional = categorySpendingService.findById(id);
        if (!categorySpendingOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categorySpendingService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<CategorySpending> save(@RequestBody CategorySpending categorySpending) {
        return new ResponseEntity<>(categorySpendingService.save(categorySpending), HttpStatus.OK);
    }

    @PutMapping("{id}")
    private ResponseEntity<CategorySpending> update(@PathVariable Long id, @RequestBody CategorySpending categorySpending) {
        Optional<CategorySpending> categorySpendingOptional = categorySpendingService.findById(id);
        if (!categorySpendingOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categorySpending.setId(id);
        return new ResponseEntity<>(categorySpendingService.save(categorySpending), HttpStatus.OK);
    }
}
