package com.example.airbnb.repository;

import com.example.airbnb.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

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


    @Query(value = "select c.name, t.id, t.note, t.time, t.total_spent,t.category_id, t.wallet_id, w.name, u.username\n" +
            "from transaction t\n" +
            "join category c on t.category_id = c.id\n" +
            "join wallet w on t.wallet_id = w.id\n" +
            "join user_table u on  w.user_id = u.id\n" +
            "where u.id = :id", nativeQuery = true)
    Iterable<Transaction> findAllByUser_Id(@PathVariable Long id);

    @Query(value = "select c.name, t.id, t.note, t.time, t.total_spent,t.category_id, t.wallet_id, w.name, u.username\n" +
            "from transaction t\n" +
            "join category c on t.category_id = c.id\n" +
            "join wallet w on t.wallet_id = w.id\n" +
            "join user_table u on  w.user_id = u.id\n" +
            "where u.id = :id and t.category_id = 1", nativeQuery = true)
    Iterable<Transaction> findAllByCategoryIncomeUser_Id(@PathVariable Long id);
    @Query(value = "select c.name, t.id, t.note, t.time, t.total_spent,t.category_id, t.wallet_id, w.name, u.username\n" +
            "from transaction t\n" +
            "join category c on t.category_id = c.id\n" +
            "join wallet w on t.wallet_id = w.id\n" +
            "join user_table u on  w.user_id = u.id\n" +
            "where u.id = :id and t.category_id = 2", nativeQuery = true)
    Iterable<Transaction> findAllByCategoryExpenseUser_Id(@PathVariable Long id);
    @Query(value = "select t.id, t.note, t.time, t.total_spent,t.category_id, t.wallet_id\n" +
            "from transaction t\n" +
            "where wallet_id = :id", nativeQuery = true)
    Iterable<Transaction> findAllByWallet(@PathVariable Long id);

}
