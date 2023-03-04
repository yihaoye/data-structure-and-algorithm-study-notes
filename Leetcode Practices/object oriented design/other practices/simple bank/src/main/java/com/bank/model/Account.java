package com.bank.model;

import java.math.*;

public class Account {
    private String customerName;
    private BigDecimal balance;

    public Account(String customerName) {
        this.customerName = customerName;
        this.balance = BigDecimal.ZERO;
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
            throw new RuntimeException("Account balance cannot be negative.");
        }
        this.balance = balance;
    }
}
