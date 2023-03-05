package com.bank;

import java.util.*;
import java.math.*;
import com.bank.model.*;
import com.bank.utils.*;

public class SolutionTest {
    public static void main(String[] args) {
        // test method include object creation and behaviour
        testSingleAccountOperations();
        testMultipleAccountsOperations();
        testInvalidAccountOperations();
    }

    public static final String CUSTOMER_A = "Alice";
    public static final String CUSTOMER_B = "Ana";
    public static final String CUSTOMER_C = "Bob";

    public static void testSingleAccountOperations() { // test 1 test alice operation
        Bank anz = new Bank(BankCode.ANZ);
        Customer customerA = new Customer(CUSTOMER_A);
        Long customerAANZAccId = anz.createAccount(customerA.getName()); // create an account and return account id - like get a bank card

        BigDecimal depositA1 = BigDecimal.valueOf(30.0);
        anz.deposit(customerAANZAccId, depositA1); // simulate insert the card or id and deposit
        BigDecimal totalBalance = depositA1;
        assert anz.getAccountBalance(customerAANZAccId).equals(depositA1);
        assert anz.getTotalBalance().equals(totalBalance) : String.format("Failed: %s total balance should be %s but is %s", anz.getBankCode(), totalBalance, anz.getTotalBalance());

        BigDecimal depositA2 = BigDecimal.valueOf(20.0);
        anz.withdraw(customerAANZAccId, depositA2); // simulate insert the card and withdraw
        totalBalance = totalBalance.subtract(depositA2);
        assert anz.getAccountBalance(customerAANZAccId).equals(totalBalance);
        assert anz.getTotalBalance().equals(totalBalance) : String.format("Failed: %s total balance should be %s but is %s", anz.getBankCode(), totalBalance, anz.getTotalBalance());

        try {
            anz.withdraw(customerAANZAccId, BigDecimal.valueOf(11.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try { // test edge case with customer trying to withdraw negative amount
            anz.withdraw(customerAANZAccId, BigDecimal.valueOf(-10.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try { // test edge case with customer trying to deposit zero amount
            anz.deposit(customerAANZAccId, BigDecimal.ZERO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testMultipleAccountsOperations() { // test 2 test multiple customers operations which do not affect each other's balance
        Bank asb = new Bank(BankCode.ASB);
        Customer customerB = new Customer(CUSTOMER_B);
        Long customerBASBAccId = asb.createAccount(customerB.getName());
        Customer customerC = new Customer(CUSTOMER_C);
        Long customerCASBAccId = asb.createAccount(customerC.getName());

        BigDecimal depositB1 = BigDecimal.valueOf(22.2);
        asb.deposit(customerBASBAccId, depositB1);
        BigDecimal depositC1 = BigDecimal.valueOf(50);
        asb.deposit(customerCASBAccId, depositC1);
        assert asb.getAccountBalance(customerBASBAccId).equals(depositB1) : String.format("Failed: Account %d balance should be %s but is %s", customerBASBAccId, depositB1, asb.getAccountBalance(customerBASBAccId));
        assert asb.getAccountBalance(customerCASBAccId).equals(depositC1) : String.format("Failed: Account %d balance should be %s but is %s", customerCASBAccId, depositC1, asb.getAccountBalance(customerCASBAccId));
        BigDecimal totalBalance = depositB1.add(depositC1);
        assert asb.getTotalBalance().equals(totalBalance) : String.format("Failed: %s total balance should be %s but is %s", asb.getBankCode(), totalBalance, asb.getTotalBalance());

        BigDecimal depositC2 = BigDecimal.valueOf(10.5);
        asb.withdraw(customerCASBAccId, depositC2);
        assert asb.getAccountBalance(customerBASBAccId).equals(depositB1) : String.format("Failed: Account %d balance should be %s but is %s", customerBASBAccId, depositB1, asb.getAccountBalance(customerBASBAccId));
        assert asb.getAccountBalance(customerCASBAccId).equals(depositC1.subtract(depositC2)) : String.format("Failed: Account %d balance should be %s but is %s", customerCASBAccId, depositC1.subtract(depositC2), asb.getAccountBalance(customerCASBAccId));
        totalBalance = totalBalance.subtract(depositC2);
        assert asb.getTotalBalance().equals(totalBalance) : String.format("Failed: %s total balance should be %s but is %s", asb.getBankCode(), totalBalance, asb.getTotalBalance());
    }

    public static void testInvalidAccountOperations() { // test 3 test non-exist account operations
        Bank asb = new Bank(BankCode.ASB);
        Long fakeASBAccId = 10L;

        try {
            asb.deposit(fakeASBAccId, BigDecimal.valueOf(10.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            asb.withdraw(fakeASBAccId, BigDecimal.valueOf(10.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
