package com.test.wex.persistence;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "purchase_transaction", schema = "wextest")
public class PurchaseTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="description")
    private String description;

    @Column(name="transaction_date")
    private LocalDate transactionDate;
    @Column(name="amount_USD")
    private String amountUSD;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }
}
