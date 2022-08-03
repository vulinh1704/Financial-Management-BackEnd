package com.example.airbnb.service.impl;

import com.example.airbnb.model.Transaction;
import com.example.airbnb.repository.TransactionRepository;
import com.example.airbnb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Iterable<Transaction> findAllByTimeBetween(String startTime, String endTime) {
        return transactionRepository.findAllByTimeBetween(startTime, endTime);
    }
}
