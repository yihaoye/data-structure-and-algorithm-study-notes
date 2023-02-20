import java.util.*;
import java.math.*;

public class Solution {
    public static void main(String[] args) {
        // test ...
        Bank anz = new Bank(BankCode.ANZ);
        Customer alice = new Customer("Alice");
        Long aliceANZAccId = anz.createAccount(alice.name);

        anz.deposit(aliceANZAccId, 30.0);
        anz.withdraw(aliceANZAccId, 20.0);
        System.out.println(anz.getAccountBalance(aliceANZAccId));
        System.out.println(anz.getTotalBalance());

        try {
            anz.withdraw(aliceANZAccId, 11.0);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



/**
    ./model/
 */
static class Bank {
    private BankCode bankCode;
    private double totalBalance; // should apply BigDecimal for more standard cases
    private Map<Long, Account> accounts; // <accountId, account>
    private Long accIdInc; 

    public Bank(BankCode bankCode) {
        this.bankCode = bankCode;
        this.totalBalance = 0.0;
        this.accounts = new HashMap<>();
        this.accIdInc = 0L;
    }

    public BankCode getBankCode() {
        return bankCode;
    }

    public double getTotalBalance() {
        return this.totalBalance;
    }

    public double getAccountBalance(Long accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("Account does not exist.");
        }
        return account.getBalance();
    }

    public double withdraw(Long accountId, double withdrawAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("Account does not exist.");
        }
        double accountBalance = account.getBalance();
        if (accountBalance < withdrawAmount) {
            throw new RuntimeException("Account balance cannot be negative.");
        }
        account.setBalance(accountBalance - withdrawAmount);
        this.totalBalance -= withdrawAmount;
        return withdrawAmount;
    }

    public void deposit(Long accountId, double depositAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("Account does not exist.");
        }
        if (depositAmount < 0.0) {
            throw new RuntimeException("Deposit cannot be negative.");
        }
        double accountBalance = account.getBalance();
        account.setBalance(accountBalance + depositAmount);
        this.totalBalance += depositAmount;
    }

    public Long createAccount(String customerName) {
        Account account = new Account(customerName);
        accounts.put(accIdInc, account);
        return accIdInc++;
    }
}

static class Account {
    private String customerName;
    private double balance;

    public Account(String customerName) {
        this.customerName = customerName;
        this.balance = 0.0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0.0) {
            throw new RuntimeException("Account balance cannot be negative.");
        }
        this.balance = balance;
    }
}

static class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



/**
    ./utils/
 */
public enum BankCode {
    ANZ, ASB, UNKNOWN
}
