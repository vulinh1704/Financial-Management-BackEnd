package com.example.airbnb.repository;

import com.example.airbnb.model.CategorySpending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySpendingRepository extends JpaRepository<CategorySpending, Long> {
}
