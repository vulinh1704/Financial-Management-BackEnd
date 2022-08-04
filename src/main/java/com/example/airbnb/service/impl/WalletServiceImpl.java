package com.example.airbnb.service.impl;

import com.example.airbnb.model.Wallet;
import com.example.airbnb.repository.WalletRepository;
import com.example.airbnb.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Iterable<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Iterable<Wallet> findAllByStatus(Long id) {
        return walletRepository.findAllByStatus(id);
    }

    @Override
    public Iterable<Wallet> findAllByStatusPublicAndUser_Id(Long id) {
        return walletRepository.findAllByStatusPublicAndUser_Id(id);
    }

    @Override
    public Iterable<Wallet> findAllByStatusPrivateAndUser_Id(Long id) {
        return walletRepository.findAllByStatusPrivateAndUser_Id(id);
    }
}

