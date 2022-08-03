package com.example.airbnb.service;
import com.example.airbnb.model.Transaction;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);

    Iterable<Transaction>findAllByTimeBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
