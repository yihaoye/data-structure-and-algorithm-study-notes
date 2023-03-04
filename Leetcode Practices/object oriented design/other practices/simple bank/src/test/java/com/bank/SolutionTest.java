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
        anz.getAccountBalance(customerAANZAccId);
        assert anz.getTotalBalance().equals(depositA1);

        BigDecimal depositA2 = BigDecimal.valueOf(20.0);
        anz.withdraw(customerAANZAccId, depositA2); // simulate insert the card and withdraw
        anz.getAccountBalance(customerAANZAccId);
        assert anz.getTotalBalance().equals(depositA1.subtract(depositA2));

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
        assert asb.getAccountBalance(customerBASBAccId).equals(depositB1);
        assert asb.getAccountBalance(customerCASBAccId).equals(depositC1);
        assert asb.getTotalBalance().equals(depositB1.add(depositC1));

        BigDecimal depositC2 = BigDecimal.valueOf(10.5);
        asb.withdraw(customerCASBAccId, depositC2);
        assert asb.getAccountBalance(customerBASBAccId).equals(depositB1);
        assert asb.getAccountBalance(customerCASBAccId).equals(depositC1.subtract(depositC2));
        assert asb.getTotalBalance().equals(depositB1.add(depositC1).subtract(depositC2));
    }
}
