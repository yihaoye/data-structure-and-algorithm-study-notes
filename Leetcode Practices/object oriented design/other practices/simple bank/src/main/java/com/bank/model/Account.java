package com.bank.model;

import java.math.*;
import java.util.*;
import java.time.*;
import com.bank.utils.*;
import com.bank.exception.*;

public class Account {
    private String customerName;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Account(String customerName) {
        this.customerName = customerName;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperationException("Account balance cannot be negative.");
        }
        this.balance = balance;
    }

    public void addDepositRecord(BigDecimal deposit) {
        this.transactions.add(new Transaction(TransactionType.DEPOSIT, deposit, LocalDateTime.now()));
    }

    public void addWithdrawalRecord(BigDecimal withdrawal) {
        this.transactions.add(new Transaction(TransactionType.WITHDRAW, withdrawal, LocalDateTime.now()));
    }

    public List<Transaction> getDepositHistory() {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (t.getType() == TransactionType.DEPOSIT) deposits.add(t);
        }
        return deposits;
    }

    public List<Transaction> getWithdrawHistory() {
        List<Transaction> withdarws = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (t.getType() == TransactionType.WITHDRAW) withdarws.add(t);
        }
        return withdarws;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(this.transactions);
    }
}
