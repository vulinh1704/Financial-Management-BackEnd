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

    @Autowired
    private TransactionService transactionService;

    @GetMapping("find-by-user/{id}")
    public ResponseEntity<Iterable<Wallet>> findAll(@PathVariable Long id) {
        return new ResponseEntity<>(walletService.findAllByStatus(id), HttpStatus.OK);
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

    @PutMapping("/delete/{id}")
    public ResponseEntity<Wallet> deleteWallet(@PathVariable Long id, @RequestBody Wallet wallet) {
        Optional<Wallet> walletDelete = walletService.findById(id);
        if (!walletDelete.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        walletDelete.get().setStatus(0);
        wallet.setId(id);
        wallet.setStatus(walletDelete.get().getStatus());
        return new ResponseEntity<>(walletService.save(wallet), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<Iterable<Wallet>> findAllByStatusPrivateAndUser_Id(@PathVariable Long id) {
        return new ResponseEntity<>(walletService.findAllByStatusPrivateAndUser_Id(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-ownerId")
    public ResponseEntity<Iterable<Wallet>> findAllByStatusPublicAndUser_Id(@RequestParam Long id) {
        return new ResponseEntity<>(walletService.findAllByStatusPublicAndUser_Id(id), HttpStatus.OK);
    }

    @PutMapping("/edit-money-type/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable Long id, @RequestBody Wallet wallet) {
        Optional<Wallet> walletOptional = walletService.findById(id);
        if (!walletOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (walletOptional.get().getMoneyType().getId().equals(wallet.getMoneyType().getId())) {
            wallet.setId(id);
            return new ResponseEntity<>(walletService.save(wallet), HttpStatus.OK);
        } else {
            wallet.setId(walletOptional.get().getId());
            Iterable<Transaction> transactions = transactionService.findAllByWallet(walletOptional.get().getId());
            if (walletOptional.get().getMoneyType().getId() == 1) {
                wallet.setMoneyAmount(Math.ceil((walletOptional.get().getMoneyAmount() / 23000) * 100) / 100);
            } else {
                wallet.setMoneyAmount(Math.ceil((walletOptional.get().getMoneyAmount() * 23000) * 100) / 100);
            }
            for (Transaction transaction : transactions) {
                if (walletOptional.get().getMoneyType().getId() == 1) {
                    transaction.setId(transaction.getId());
                    transaction.setTotalSpent(Math.ceil((transaction.getTotalSpent() / 23000) * 100) / 100);
                    transactionService.save(transaction);
                } else {
                    transaction.setId(transaction.getId());
                    transaction.setTotalSpent(Math.ceil((transaction.getTotalSpent() * 23000) * 100) / 100);
                    transactionService.save(transaction);
                }
            }
            wallet.setIcon(walletOptional.get().getIcon());
            wallet.setName(walletOptional.get().getName());
            wallet.setUser(walletOptional.get().getUser());
            wallet.setStatus(walletOptional.get().getStatus());
            walletService.save(wallet);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Wallet> updateStatus(@PathVariable Long id, @RequestBody Wallet wallet) {
        Optional<Wallet> walletOptional = walletService.findById(id);
        if (!walletOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wallet.setId(id);
        if (wallet.getStatus() == 1) {
            wallet.setStatus(2);
        }
        if (wallet.getStatus() == 2) {
            wallet.setStatus(1);
        }
        return new ResponseEntity<>(walletService.save(wallet), HttpStatus.OK);
    }
}
