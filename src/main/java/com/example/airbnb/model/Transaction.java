package com.example.airbnb.model;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalSpent;
    @ManyToOne
    @JoinColumn(name = "categorySpending_id")
    private CategorySpending categorySpending;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    private String note;
    private String time;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public CategorySpending getCategorySpending() {
        return categorySpending;
    }

    public void setCategorySpending(CategorySpending categorySpending) {
        this.categorySpending = categorySpending;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
