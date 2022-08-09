package com.example.airbnb.controller;

import com.example.airbnb.model.Category;
import com.example.airbnb.model.Transaction;
import com.example.airbnb.model.Wallet;
import com.example.airbnb.service.CategoryService;
import com.example.airbnb.service.TransactionService;
import com.example.airbnb.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("find-by-wallet/{id}")
    public ResponseEntity<Iterable<Transaction>> findAll(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.findAllByWallet_Id(id), HttpStatus.OK);
    }

    @GetMapping("find-by-category/{id}")
    public ResponseEntity<Iterable<Transaction>> findAllByCategory_Id(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.findAllByCategory_Id(id), HttpStatus.OK);
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
        Optional<Category> category = categoryService.findById(transaction.getCategory().getId());
        transaction.getCategory().setStatus(category.get().getStatus());
        transactionService.save(transaction);
        if (transaction.getCategory().getStatus() == 1) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + transaction.getTotalSpent());
            walletService.save(wallet.get());
        } else {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() - transaction.getTotalSpent());
            walletService.save(wallet.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> removeTransaction(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        Optional<Wallet> editWallet = walletService.findById(transaction.get().getWallet().getId());
        editWallet.get().setId(transaction.get().getWallet().getId());
        if (transaction.get().getCategory().getStatus() == 1) {
            editWallet.get().setMoneyAmount(editWallet.get().getMoneyAmount() - transaction.get().getTotalSpent());
        } else {
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
        } else if ((oldTransaction == 2) && (newTransaction == 1)) {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + editTransaction.get().getTotalSpent() + transaction.getTotalSpent());
        } else {
            wallet.get().setMoneyAmount(wallet.get().getMoneyAmount() + editTransaction.get().getTotalSpent() - transaction.getTotalSpent());
        }
        transactionService.save(transaction);
        walletService.save(wallet.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("find-all-by-time")
    public ResponseEntity<Iterable<Transaction>> findAllByMonthTimeAndYearTime(@RequestParam("status") int status, @RequestParam("id") int id) {
        String month = String.valueOf(YearMonth.now());
        return new ResponseEntity<>(transactionService.findAllByMonthTimeAndYearTime(status, month, id), HttpStatus.OK);
    }

    @GetMapping("find-all-by-time2")
    public ResponseEntity<Iterable<Transaction>> findAllByMonthTimeAndYearTime(@RequestParam("id") int id) {
        String month = String.valueOf(YearMonth.now());
        return new ResponseEntity<>(transactionService.findAllByMonthTimeAndYearTime(2, month, id), HttpStatus.OK);
    }

    @GetMapping("/find-all-income-6month/{id}")
    public ResponseEntity<HashMap<Integer, Iterable<Transaction>>> findAllTransactionsIncomeFor6Months(@PathVariable Long id) {
        HashMap<Integer, Iterable<Transaction>> transactionIncome = new HashMap<>();
        String presentTime = String.valueOf(java.time.LocalDate.now());
        String[] time = presentTime.split("-");
        int firstYear = Integer.parseInt(time[0]);
        int firstMonth = Integer.parseInt(time[1]);
        int firstDay = Integer.parseInt(time[2]) - Integer.parseInt(time[2]) + 1;
        String currentMonth;
        if (firstMonth < 10) {
            currentMonth = firstYear + "-0" + firstMonth + "-0" + firstDay;
        } else {
            currentMonth = firstYear + "-" + firstMonth + "-0" + firstDay;
        }
        transactionIncome.put(firstMonth, transactionService.findAllTransactionsIncomeFor6Months(id, presentTime, currentMonth));
        firstDay = 31;
        for (int i = 1; i < 6; i++) {
            String timeNow;
            String nextTime;
            int day = 1;
            firstMonth = Integer.parseInt(time[1]) - i;
            if (firstMonth < 1) {
                firstMonth = 12;
                firstYear = firstYear - 1;
            }
            if (firstMonth < 10) {
                timeNow = firstYear + "-0" + firstMonth + "-" + firstDay;
                nextTime = firstYear + "-0" + firstMonth + "-0" + day;
            } else {
                timeNow = firstYear + "-" + firstMonth + "-" + firstDay;
                nextTime = firstYear + "-" + firstMonth + "-0" + day;
            }
            transactionIncome.put(firstMonth, transactionService.findAllTransactionsIncomeFor6Months(id, timeNow, nextTime));
        }
        System.out.println(transactionIncome);
        return new ResponseEntity<>(transactionIncome, HttpStatus.OK);
    }

    @GetMapping("/find-all-expense-6month/{id}")
    public ResponseEntity<HashMap<Integer, Iterable<Transaction>>> findAllTransactionsExpenseFor6Months(@PathVariable Long id) {
        HashMap<Integer, Iterable<Transaction>> transactionExpense = new HashMap<>();
        String presentTime = String.valueOf(java.time.LocalDate.now());
        String[] time = presentTime.split("-");
        int firstYear = Integer.parseInt(time[0]);
        int firstMonth = Integer.parseInt(time[1]);
        int firstDay = Integer.parseInt(time[2]) - Integer.parseInt(time[2]) + 1;
        String currentMonth;
        if (firstMonth < 10) {
            currentMonth = firstYear + "-0" + firstMonth + "-0" + firstDay;
        } else {
            currentMonth = firstYear + "-" + firstMonth + "-0" + firstDay;
        }
        transactionExpense.put(firstMonth, transactionService.findAllTransactionsExpenseFor6Months(id, presentTime, currentMonth));
        firstDay = 31;
        for (int i = 1; i < 6; i++) {
            String timeNow;
            String nextTime;
            int day = 1;
            firstMonth = Integer.parseInt(time[1]) - i;
            if (firstMonth < 1) {
                firstMonth = 12;
                firstYear = firstYear - 1;
            }
            if (firstMonth < 10) {
                timeNow = firstYear + "-0" + firstMonth + "-" + firstDay;
                nextTime = firstYear + "-0" + firstMonth + "-0" + day;
            } else {
                timeNow = firstYear + "-" + firstMonth + "-" + firstDay;
                nextTime = firstYear + "-" + firstMonth + "-0" + day;
            }
            transactionExpense.put(firstMonth, transactionService.findAllTransactionsExpenseFor6Months(id, timeNow, nextTime));
        }
        System.out.println(transactionExpense);
        return new ResponseEntity<>(transactionExpense, HttpStatus.OK);
    }

    @GetMapping("find-all-transaction")
    public ResponseEntity<Iterable<Transaction>> findAllTransactions(@RequestParam String startTime, @RequestParam String endTime, @RequestParam Long status, @RequestParam Long from, @RequestParam Long to, @RequestParam Long id) {
        if (startTime.equals("")) {
            startTime = "1900-01-01";
        }
        if (endTime.equals("")) {
            endTime = "3000-01-01";
        }
        return new ResponseEntity<>(transactionService.findAllByTransaction(String.valueOf(LocalDate.parse(startTime)), String.valueOf(LocalDate.parse(endTime)), status, from, to, id), HttpStatus.OK);
    }
}
