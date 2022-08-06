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
@RequestMapping("transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @GetMapping("find-by-wallet/{id}")
    public ResponseEntity<Iterable<Transaction>> findAll(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.findAllByWallet_Id(id), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        Optional<Transaction> optionalTransaction = transactionService.findById(id);
        if (!optionalTransaction.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactionService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Transaction> save(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.save(transaction), HttpStatus.OK);
    }

    @PutMapping("{id}")
    private ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody Transaction transaction) {
        Optional<Transaction> optionalTransaction = transactionService.findById(id);
        if (!optionalTransaction.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transaction.setId(id);
        return new ResponseEntity<>(transactionService.save(transaction), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Optional<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        Optional<Wallet> wallet = walletService.findById(transaction.getWallet().getId());
        transactionService.save(transaction);
        if (transaction.getCategory().getStatus() == 1) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + transaction.getTotalSpent());
            walletService.save(wallet.get());
        }else {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() - transaction.getTotalSpent());
            walletService.save(wallet.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Transaction> removeTransaction(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        Optional<Wallet> editWallet = walletService.findById(transaction.get().getWallet().getId());
        editWallet.get().setId(transaction.get().getWallet().getId());
        if (transaction.get().getCategory().getStatus() == 1) {
            editWallet.get().setMoneyAmount(editWallet.get().getMoneyAmount() - transaction.get().getTotalSpent());
        }else {
            editWallet.get().setMoneyAmount(editWallet.get().getMoneyAmount() + transaction.get().getTotalSpent());
        }
        walletService.save(editWallet.get());
        transactionService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Optional<Transaction>> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Optional<Transaction> editTransaction = transactionService.findById(id);
        Optional<Wallet> wallet = walletService.findById(editTransaction.get().getWallet().getId());
        transaction.setId(id);
        int oldTransaction = editTransaction.get().getCategory().getStatus();
        int newTransaction = transaction.getCategory().getStatus();
        wallet.get().setId(wallet.get().getId());
        if ((oldTransaction == 1) && (newTransaction == 1)) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() - editTransaction.get().getTotalSpent() + transaction.getTotalSpent());
        } else if ((oldTransaction == 1) && (newTransaction == 2)) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() - editTransaction.get().getTotalSpent() - transaction.getTotalSpent());
        }else if ((oldTransaction == 2) && (newTransaction == 1)) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + editTransaction.get().getTotalSpent() + transaction.getTotalSpent());
        } else {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + editTransaction.get().getTotalSpent() - transaction.getTotalSpent());
        }
        transactionService.save(transaction);
        walletService.save(wallet.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
