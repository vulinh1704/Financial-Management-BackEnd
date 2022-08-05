package com.example.airbnb.repository;

import com.example.airbnb.model.Transaction;
import com.example.airbnb.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select * from financial_management.transaction where wallet_id=?",nativeQuery = true)
    Iterable<Transaction>findAllByWallet_Id(Long id);

}
