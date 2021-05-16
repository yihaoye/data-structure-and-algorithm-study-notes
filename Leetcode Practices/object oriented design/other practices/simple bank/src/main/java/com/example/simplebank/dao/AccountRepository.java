package com.example.simplebank.dao;

import com.example.simplebank.model.Account;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {
    private static Map<String, Account> accounts = new HashMap<>();

    public void saveAccount(Account account) {
        synchronized (accounts) {
            accounts.putIfAbsent(account.getAccountNumber(), account);
        }
    }

    public void updateAccount(Account account) {
        synchronized (accounts) {
            accounts.put(account.getAccountNumber(), account);
        }
    }

    public void deleteAll() {
        synchronized (accounts) {
            accounts = new HashMap<>();
        }
    }

    public List<Account> findByBankCode(String bankCode) {
        List<Account> res = new ArrayList<>();
        synchronized (accounts) {
            for (Account account : accounts.values()) {
                if (account.getBankCode().equals(bankCode)) res.add(account);
            }
        }
        return res;
    }

    public Account findByAccountNumber(String accountNumber) {
        synchronized (accounts) {
            return accounts.get(accountNumber);
        }
    }
}
