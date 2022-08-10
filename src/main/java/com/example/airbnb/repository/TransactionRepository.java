package com.example.airbnb.repository;

import com.example.airbnb.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select * from financial_management.transaction where category_id= :id ", nativeQuery = true)
    Iterable<Transaction> findAllByCategory_Id(Long id);

    @Query(value = "select * from `financial_management`.transaction where wallet_id=? order by id DESC;", nativeQuery = true)
    Iterable<Transaction> findAllByWallet_Id(Long id);

    @Query(value = "select * from `financial-management`.transaction\n" +
            "where time between startTime and endTime", nativeQuery = true)
    Iterable<Transaction> findAllByTimeBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);


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

    @Query(value = "select t.id, t.note, t.time, t.total_spent, t.wallet_id, t.category_id\n" +
            "from transaction t\n" +
            "         join category c on c.id = t.category_id\n" +
            "where c.status = :status\n" +
            "  and t.time like concat('%',:month, '%') and t.wallet_id  = :id", nativeQuery = true)
    Iterable<Transaction> findAllByMonthTimeAndYearTime(@Param("status") int status, @Param("month") String month,@Param("id") int id);

    @Query(value = "select * from transaction t\n" +
            "join wallet w on t.wallet_id = w.id\n" +
            "join category c on c.id = t.category_id\n" +
            "where t.time >= :sixMonthsAgo and time<= :presentTime and w.id = :id and c.status = 1",nativeQuery = true)
    Iterable<Transaction>findAllTransactionsIncomeFor6Months(@PathVariable Long id, @Param("presentTime") String presentTime, @Param("sixMonthsAgo") String sixMonthsAgo);
    @Query(value = "select * from transaction t\n" +
            "join wallet w on t.wallet_id = w.id\n" +
            "join category c on c.id = t.category_id\n" +
            "where t.time >= :sixMonthsAgo and time<= :presentTime and w.id = :id and c.status = 2",nativeQuery = true)
    Iterable<Transaction>findAllTransactionsExpenseFor6Months(@PathVariable Long id, @Param("presentTime") String presentTime, @Param("sixMonthsAgo") String sixMonthsAgo);

    @Query(value = "select transaction.id, transaction.note,transaction.total_spent,category_id,wallet_id,transaction.time\n" +
            "from transaction join category c on c.id = transaction.category_id\n" +
            "where time >= :startTime and time<= :endTime and c.status= :status and total_spent between :from and :to and wallet_id = :id",nativeQuery = true)
    Iterable<Transaction>findAllByTransaction(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("status")Long status,@Param("from")Long from,@Param("to")Long to,@Param("id")Long id);

}
