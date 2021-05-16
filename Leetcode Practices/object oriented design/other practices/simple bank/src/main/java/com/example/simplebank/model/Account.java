package com.example.simplebank.model;

import com.example.simplebank.utils.BankCode;
import java.math.BigDecimal;

public class Account {
        private BankCode bankCode;
        private String accountNumber;
        private String customerName;
        private BigDecimal totalBalance;

        public Account(BankCode bankCode, String accountNumber, String customerName) {
                this.bankCode = bankCode;
                this.accountNumber = accountNumber;
                this.customerName = customerName;
                this.totalBalance = BigDecimal.ZERO;
        }

        public BankCode getBankCode() {
                return bankCode;
        }

        public String getAccountNumber() {
                return accountNumber;
        }

        public String getCustomerName() {
                return customerName;
        }

        public void setCustomerName(String customerName) {
                this.customerName = customerName;
        }

        public BigDecimal getTotalBalance() {
                return totalBalance;
        }

        public void setTotalBalance(BigDecimal totalBalance) {
                if (totalBalance.compareTo(BigDecimal.ZERO) < 0) {
                        throw new RuntimeException("Account balance cannot be negative.");
                }
                this.totalBalance = totalBalance;
        }
}
