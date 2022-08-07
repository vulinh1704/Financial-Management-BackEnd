package com.example.airbnb.service;

import com.example.airbnb.model.Transaction;


import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);


    Iterable<Transaction> findAllByWallet_Id(Long id);

    Iterable<Transaction> findAllByWallet(Long id);

    void remove(Long id);

    Iterable<Transaction> findAllByMonthTimeAndYearTime(int status, String month);

}
