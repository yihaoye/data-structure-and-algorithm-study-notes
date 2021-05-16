package com.example.simplebank.dao;

import com.example.simplebank.model.Bank;
import com.example.simplebank.utils.BankCode;
import java.util.HashMap;
import java.util.Map;

public class BankRepository {
    private static Map<BankCode, Bank> banks = new HashMap<>();

    public void saveBank(Bank bank) {
        synchronized (banks) {
            banks.putIfAbsent(bank.getBankCode(), bank);
        }
    }

    public void updateBank(Bank bank) {
        synchronized (banks) {
            banks.put(bank.getBankCode(), bank);
        }
    }

    public void deleteAll() {
        synchronized (banks) {
            banks = new HashMap<>();
        }
    }

    public Bank findByBankCode(BankCode bankCode) {
        synchronized (banks) {
            return banks.get(bankCode);
        }
    }
}
