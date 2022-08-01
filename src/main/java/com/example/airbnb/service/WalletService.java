package com.example.airbnb.service;
import com.example.airbnb.model.Wallet;

import java.util.Optional;

public interface WalletService {
    Wallet save(Wallet wallet);

    Iterable<Wallet> findAll();

    Optional<Wallet> findById(Long id);
}
