package com.example.airbnb.service;
import com.example.airbnb.model.Wallet;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface WalletService {
    Wallet save(Wallet wallet);

    Iterable<Wallet> findAll();

    Optional<Wallet> findById(Long id);

    Iterable<Wallet> findAllByStatus( Long id);

   Iterable<Wallet> findAllByStatusPublicAndUser_Id(@Param("id") Long id);
   Iterable<Wallet> findAllByStatusPrivateAndUser_Id(@Param("id") Long id);
}
