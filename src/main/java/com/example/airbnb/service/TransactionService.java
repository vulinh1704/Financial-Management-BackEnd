package com.example.airbnb.service;
import com.example.airbnb.model.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);

    Iterable<Transaction>findAllByTimeBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);
    Iterable<Transaction> findAllByUser_Id(@PathVariable Long id);
    Iterable<Transaction> findAllByCategoryIncomeUser_Id(@PathVariable Long id);

    Iterable<Transaction> findAllByCategoryExpenseUser_Id(@PathVariable Long id);
    Iterable<Transaction> findAllByWallet(Long id);
    void remove(Long id);

}
