package com.bank.model;

import java.util.*;
import java.math.*;
import com.bank.utils.*;

public class Bank {
    private BankCode bankCode;
    private BigDecimal totalBalance;
    private Map<Long, Account> accounts; // <accountId, account>
    // private Map<Long, Set<Account>> customersAccounts; // <customerId, <accountA, accountB, ...>> a customer may have several accounts, the field could support advance service i.e. find customer accounts
    private Long accIdInc; // accountId can apply UUID.randomUUID().toString() for some standard scenario

    public Bank(BankCode bankCode) {
        this.bankCode = bankCode;
        this.totalBalance = BigDecimal.ZERO;
        this.accounts = new HashMap<>();
        this.accIdInc = 0L;
    }

    public BankCode getBankCode() {
        return this.bankCode;
    }

    public BigDecimal getTotalBalance() {
        System.out.println(String.format("Bank %s remain total balance: $%s", this.bankCode, this.totalBalance));
        return this.totalBalance;
    }

    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException(String.format("Account %d does not exist.", accountId));
        }
        System.out.println(String.format("Account %d (%s) remain balance: $%s", accountId, account.getCustomerName(), account.getBalance()));
        return account.getBalance();
    }

    public BigDecimal withdraw(Long accountId, BigDecimal withdrawAmount) {
        if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdraw amount should be positive.");
        }

        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException(String.format("Account %d does not exist.", accountId));
        }

        BigDecimal accountBalance = account.getBalance();
        if (accountBalance.compareTo(withdrawAmount) < 0) {
            throw new RuntimeException("Account balance insufficient to withdraw.");
        }

        /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
        account.setBalance(accountBalance.subtract(withdrawAmount));
        this.totalBalance = this.totalBalance.subtract(withdrawAmount); // withdraw operation only check Account balance but not bank total balance since if Account balance is always not negative then bank total balance will not be negative.
        System.out.println(String.format("Account %d (%s) withdraw: $%s", accountId, account.getCustomerName(), withdrawAmount));
        return withdrawAmount;
    }

    public void deposit(Long accountId, BigDecimal depositAmount) {
        if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount should be positive.");
        }

        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException(String.format("Account %d does not exist.", accountId));
        }

        BigDecimal accountBalance = account.getBalance();
        /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
        account.setBalance(accountBalance.add(depositAmount));
        this.totalBalance = this.totalBalance.add(depositAmount);
        System.out.println(String.format("Account %d (%s) deposit: $%s", accountId, account.getCustomerName(), depositAmount));
    }

    public Long createAccount(String customerName) { // apply CustomerDTO for improvement or consider Dependency Injection way for improvement if needed - public Long registerAccount(Account account) {...} + Account is interface + accountImpl instance created by Factory etc
        Account account = new Account(customerName);
        accounts.put(accIdInc, account);
        System.out.println(String.format("Customer %s create an account: %d in bank %s", customerName, accIdInc, bankCode));
        return accIdInc++;
    }
}
