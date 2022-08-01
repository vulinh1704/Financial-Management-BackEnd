package com.example.airbnb.controller;

import com.example.airbnb.model.Transaction;
import com.example.airbnb.model.Wallet;
import com.example.airbnb.service.TransactionService;
import com.example.airbnb.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping
    public ResponseEntity<Iterable<Wallet>> findAll() {
        return new ResponseEntity<>(walletService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Wallet> findById(@PathVariable Long id) {
        Optional<Wallet> optionalWallet = walletService.findById(id);
        if (!optionalWallet.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(walletService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Wallet> save(@RequestBody Wallet wallet) {
        return new ResponseEntity<>(walletService.save(wallet), HttpStatus.OK);
    }

    @PutMapping("{id}")
    private ResponseEntity<Wallet> update(@PathVariable Long id, @RequestBody Wallet wallet) {
        Optional<Wallet> walletOptional = walletService.findById(id);
        if (!walletOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wallet.setId(id);
        return new ResponseEntity<>(walletService.save(wallet), HttpStatus.OK);
    }
}
