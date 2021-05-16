package com.example.simplebank;

import com.example.simplebank.dao.AccountRepository;
import com.example.simplebank.dao.BankRepository;

public class DatabaseCleanupService {
    private static BankRepository bankRepository = new BankRepository();
    private static AccountRepository accountRepository = new AccountRepository();

    public static void truncate() {
        bankRepository.deleteAll();
        accountRepository.deleteAll();
    }
}
