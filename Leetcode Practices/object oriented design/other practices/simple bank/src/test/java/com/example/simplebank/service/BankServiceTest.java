package com.example.simplebank.service;

import com.example.simplebank.DatabaseCleanupService;
import com.example.simplebank.model.Account;
import com.example.simplebank.model.Customer;
import com.example.simplebank.utils.BankCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankServiceTest {

    @BeforeEach
    public void setup() {
        DatabaseCleanupService.truncate();
    }

    @Test
    public void testAction() {
        BankService bankService = new BankService();
        String accountNumber = UUID.randomUUID().toString();
        Customer customer = new Customer(accountNumber, "Alice");
        bankService.createAccount(BankCode.ANZ, customer);

        bankService.deposit(customer.getAccountNumber(), BigDecimal.valueOf(30));
        Account bankAccount = bankService.getAccount(customer.getAccountNumber());
        assertEquals(bankAccount.getTotalBalance(), BigDecimal.valueOf(30));
        assertEquals(bankService.getBankTotalBalance(bankAccount.getBankCode()), BigDecimal.valueOf(30));

        bankService.withdraw(customer.getAccountNumber(), BigDecimal.valueOf(20));
        bankAccount = bankService.getAccount(customer.getAccountNumber());
        assertEquals(bankAccount.getTotalBalance(), BigDecimal.valueOf(10));
        assertEquals(bankService.getBankTotalBalance(bankAccount.getBankCode()), BigDecimal.valueOf(10));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bankService.withdraw(customer.getAccountNumber(), BigDecimal.valueOf(11));
        });
        String expectedMessage = "Not enough balance for account.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testMultipleCustomerAction() {
        BankService bankService = new BankService();

        String accountNumberA = UUID.randomUUID().toString();
        Customer customerA = new Customer(accountNumberA, "Ana");
        bankService.createAccount(BankCode.ANZ, customerA);

        String accountNumberB = UUID.randomUUID().toString();
        Customer customerB = new Customer(accountNumberB, "Ben");
        bankService.createAccount(BankCode.ANZ, customerB);

        bankService.deposit(customerA.getAccountNumber(), BigDecimal.valueOf(30));
        bankService.deposit(customerB.getAccountNumber(), BigDecimal.valueOf(50));
        Account bankAccountB = bankService.getAccount(customerB.getAccountNumber());
        assertEquals(bankAccountB.getTotalBalance(), BigDecimal.valueOf(50));
        assertEquals(bankService.getBankTotalBalance(bankAccountB.getBankCode()), BigDecimal.valueOf(80));

        bankService.withdraw(customerA.getAccountNumber(), BigDecimal.valueOf(20));
        Account bankAccountA = bankService.getAccount(customerA.getAccountNumber());
        assertEquals(bankAccountA.getTotalBalance(), BigDecimal.valueOf(10));
        assertEquals(bankService.getBankTotalBalance(bankAccountA.getBankCode()), BigDecimal.valueOf(60));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bankService.withdraw(customerA.getAccountNumber(), BigDecimal.valueOf(30));
        });
        String expectedMessage = "Not enough balance for account.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}