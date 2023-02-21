import java.util.*;
import java.math.*;

public class Solution {
    public static void main(String[] args) {
        // test 1 test alice
        Bank anz = new Bank(BankCode.ANZ);
        Customer alice = new Customer("Alice");
        Long aliceANZAccId = anz.createAccount(alice.name); // create an account and return account id - like get a bank card

        anz.deposit(aliceANZAccId, 30.0); // simulate insert the card or id and deposit
        anz.getAccountBalance(aliceANZAccId);
        anz.getTotalBalance();

        anz.withdraw(aliceANZAccId, 20.0); // simulate insert the card and withdraw
        anz.getAccountBalance(aliceANZAccId);
        anz.getTotalBalance();

        try {
            anz.withdraw(aliceANZAccId, 11.0);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // test 2 test multiple customers operation
        Customer bob = new Customer("Bob");
        Long bobANZAccId = anz.createAccount(bob.name);

        anz.deposit(bobANZAccId, 50.0);
        anz.getAccountBalance(aliceANZAccId);
        anz.getAccountBalance(bobANZAccId);
        anz.getTotalBalance();

        anz.withdraw(bobANZAccId, 10.5);
        anz.getAccountBalance(aliceANZAccId);
        anz.getAccountBalance(bobANZAccId);
        anz.getTotalBalance();
    }


    /**
        ./model/
    */
    static class Bank {
        private BankCode bankCode;
        private double totalBalance; // should apply BigDecimal for more standard scenario
        private Map<Long, Account> accounts; // <accountId, account>
        // private Map<Long, Set<Account>> customersAccounts; // <customerId, <accountA, accountB, ...>> a customer may have several accounts, the field could support advance service i.e. find customer accounts
        private Long accIdInc; // accountId can apply UUID.randomUUID().toString() for some standard scenario

        public Bank(BankCode bankCode) {
            this.bankCode = bankCode;
            this.totalBalance = 0.0;
            this.accounts = new HashMap<>();
            this.accIdInc = 0L;
        }

        public BankCode getBankCode() {
            return this.bankCode;
        }

        public double getTotalBalance() {
            System.out.println("Bank " + this.bankCode + " remain total balance: " + this.totalBalance);
            return this.totalBalance;
        }

        public double getAccountBalance(Long accountId) {
            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }
            System.out.println("Account " + accountId + " (" + account.customerName + ") remain balance: " + account.getBalance());
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
            System.out.println("Account " + accountId + " (" + account.customerName + ") withdraw: $" + withdrawAmount);
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
            System.out.println("Account " + accountId + " (" + account.customerName + ") deposit: $" + depositAmount);
        }

        public Long createAccount(String customerName) {
            Account account = new Account(customerName);
            accounts.put(accIdInc, account);
            System.out.println("Customer " + customerName + " create an account: " + accIdInc);
            return accIdInc++;
        }
    }

    static class Account {
        private String customerName;
        private double balance; // should apply BigDecimal for more standard scenario

        public Account(String customerName) {
            this.customerName = customerName;
            this.balance = 0.0;
        }

        public String getCustomerName() {
            return this.customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public double getBalance() {
            return this.balance;
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
        // private Long customerId; // unique id for customer

        public Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
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
}
