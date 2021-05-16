package com.example.simplebank.model;

import com.example.simplebank.utils.BankCode;

import java.math.BigDecimal;

public class Bank {
    private BankCode bankCode;
    private BigDecimal totalBalance;

    public Bank(BankCode bankCode) {
        this.bankCode = bankCode;
        this.totalBalance = BigDecimal.ZERO;
    }

    public BankCode getBankCode() {
        return bankCode;
    }

    public BigDecimal getTotalBalance() {
        return this.totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        if (totalBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Bank balance cannot be negative.");
        }
        this.totalBalance = totalBalance;
    }
}
