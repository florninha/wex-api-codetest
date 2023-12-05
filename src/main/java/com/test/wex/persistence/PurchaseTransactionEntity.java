package com.test.wex.persistence;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "purchase_transaction", schema = "wextest")
public class PurchaseTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="description")
    private String description;

    @Column(name="transaction_date")
    private Date transactionDate;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }
}
