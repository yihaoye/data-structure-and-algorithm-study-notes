package com.example.simplebank.service;

import com.example.simplebank.dao.AccountRepository;
import com.example.simplebank.dao.BankRepository;
import com.example.simplebank.exception.DataAccessException;
import com.example.simplebank.exception.OperationException;
import com.example.simplebank.model.Account;
import com.example.simplebank.model.Bank;
import com.example.simplebank.model.Customer;
import com.example.simplebank.utils.BankCode;
import java.math.BigDecimal;

public class BankService {
    private static BankRepository bankRepository = new BankRepository();
    private static AccountRepository accountRepository = new AccountRepository();

    public BankService() {
    }

    public void createAccount(BankCode bankCode, Customer customer) {
        if (customer.getAccountNumber().isEmpty()) {
            throw new DataAccessException("Account insert failed.");
        }
        if (this.bankRepository.findByBankCode(bankCode) == null) {
            this.bankRepository.saveBank(new Bank(bankCode));
        }
        Account account = new Account(bankCode, customer.getAccountNumber(), customer.getName());
        this.accountRepository.saveAccount(account);
    }

    public void updateAccount(Account account) {
        if (account.getAccountNumber().isEmpty() || this.accountRepository.findByAccountNumber(account.getAccountNumber()) == null) {
            throw new DataAccessException("Account update failed.");
        }
        this.accountRepository.updateAccount(account);
    }

    public Account getAccount(String accountNumber) {
        if (accountNumber.isEmpty() || this.accountRepository.findByAccountNumber(accountNumber) == null) {
            throw new DataAccessException("Account number not found.");
        }
        return this.accountRepository.findByAccountNumber(accountNumber);
    }

    public BigDecimal getBankTotalBalance(BankCode bankCode) {
        return this.bankRepository.findByBankCode(bankCode).getTotalBalance();
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        BigDecimal accountBalance = account.getTotalBalance();
        if (accountBalance.compareTo(amount) > 0) {
            account.setTotalBalance(accountBalance.subtract(amount));
            updateAccount(account);
            Bank bank = this.bankRepository.findByBankCode(account.getBankCode());
            BigDecimal bankBalance = bank.getTotalBalance();
            bank.setTotalBalance(bankBalance.subtract(amount));
            this.bankRepository.updateBank(bank);
        } else {
            throw new OperationException("Not enough balance for account.");
        }
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        BigDecimal accountBalance = account.getTotalBalance();
        account.setTotalBalance(accountBalance.add(amount));
        updateAccount(account);
        Bank bank = this.bankRepository.findByBankCode(account.getBankCode());
        BigDecimal bankBalance = bank.getTotalBalance();
        bank.setTotalBalance(bankBalance.add(amount));
        this.bankRepository.updateBank(bank);
    }
}
