package com.example.airbnb.controller;

import com.example.airbnb.model.Transaction;
import com.example.airbnb.service.TransactionService;
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

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> findAll() {
        return new ResponseEntity<>(transactionService.findAll(), HttpStatus.OK);
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
}
