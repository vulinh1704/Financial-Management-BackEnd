package com.example.airbnb.service;
import com.example.airbnb.model.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);


    Iterable<Transaction>findAllByWallet_Id(Long id);

    Iterable<Transaction> findAllByWallet(Long id);
    void remove(Long id);

}
