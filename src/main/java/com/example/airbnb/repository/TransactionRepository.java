package com.example.airbnb.repository;

import com.example.airbnb.model.Transaction;
import com.example.airbnb.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select * from `financial-management`.transaction " +
            "where category_spending_id=? ",nativeQuery = true)
    Iterable<Transaction>findAllByCategorySpending_Id(Long id);

    @Query(value = "select * from `financial-management`.transaction where wallet_id=?",nativeQuery = true)
    Iterable<Transaction>findAllByWallet_Id(Long id);

    @Query(value = "select * from `financial-management`.transaction\n" +
            "where time between startTime and endTime",nativeQuery = true)
    Iterable<Transaction>findAllByTimeBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);


}
