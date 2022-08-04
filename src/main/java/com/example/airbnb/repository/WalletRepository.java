package com.example.airbnb.repository;

import com.example.airbnb.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Modifying
    @Query(value = "select * from wallet where status = 1 and user_id = :id", nativeQuery = true)
    Iterable<Wallet> findAllByStatusPublicAndUser_Id(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from wallet where status = 0 and user_id = :id", nativeQuery = true)
    Iterable<Wallet> findAllByStatusPrivateAndUser_Id(@PathVariable Long id);

    @Modifying
    @Query(value = "select * from wallet where user_id = :id and (status = 1 or status = 2)", nativeQuery = true)
    Iterable<Wallet> findAllByStatus(@PathVariable Long id);
}
