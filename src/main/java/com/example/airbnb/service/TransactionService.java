package com.example.airbnb.service;

import com.example.airbnb.model.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();

    Optional<Transaction> findById(Long id);

    Iterable<Transaction> findAllByCategory_Id(Long id);

    Iterable<Transaction> findAllByWallet_Id(Long id);

    Iterable<Transaction> findAllByWallet(Long id);

    void remove(Long id);

    Iterable<Transaction> findAllByMonthTimeAndYearTime(int status, String month, int id);

    Iterable<Transaction>findAllTransactionsIncomeFor6Months(Long id, String presentTime, String sixMonthsAgo);

    Iterable<Transaction>findAllTransactionsExpenseFor6Months(Long id, String presentTime, String sixMonthsAgo);

    Iterable<Transaction>findAllByTransaction(String startTime,String endTime,Long status,Long from,Long to,Long id);
}
