package com.example.airbnb.service;
import com.example.airbnb.model.Transaction;

import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);
}
