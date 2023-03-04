import java.util.*;
import java.math.*;
import model.*;
import utils.*;

public class Solution {
    public static void main(String[] args) {
        // test method include object creation and behaviour
        testSingleAccountOperations();
        testMultipleAccountsOperations();
    }


    /**
        ./test/
     */
    public static final String CUSTOMER_A = "Alice";
    public static final String CUSTOMER_B = "Ana";
    public static final String CUSTOMER_C = "Bob";

    public static void testSingleAccountOperations() { // test 1 test alice operation
        Bank anz = new Bank(BankCode.ANZ);
        Customer customerA = new Customer(CUSTOMER_A);
        Long customerAANZAccId = anz.createAccount(customerA.getName()); // create an account and return account id - like get a bank card

        anz.deposit(customerAANZAccId, BigDecimal.valueOf(30.0)); // simulate insert the card or id and deposit
        anz.getAccountBalance(customerAANZAccId);
        anz.getTotalBalance();

        anz.withdraw(customerAANZAccId, BigDecimal.valueOf(20.0)); // simulate insert the card and withdraw
        anz.getAccountBalance(customerAANZAccId);
        anz.getTotalBalance();

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

        asb.deposit(customerBASBAccId, BigDecimal.valueOf(22.2));
        asb.deposit(customerCASBAccId, BigDecimal.valueOf(50.0));
        asb.getAccountBalance(customerBASBAccId);
        asb.getAccountBalance(customerCASBAccId);
        asb.getTotalBalance();

        asb.withdraw(customerCASBAccId, BigDecimal.valueOf(10.5));
        asb.getAccountBalance(customerBASBAccId);
        asb.getAccountBalance(customerCASBAccId);
        asb.getTotalBalance();
    }
}
