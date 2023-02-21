import java.util.*;
import java.math.*;

public class Solution {
    public static void main(String[] args) {
        testSingleAccountOperations();
        testMultipleAccountsOperations();
    }


    /**
        ./model/
    */
    static class Bank {
        private BankCode bankCode;
        private BigDecimal totalBalance;
        private Map<Long, Account> accounts; // <accountId, account>
        // private Map<Long, Set<Account>> customersAccounts; // <customerId, <accountA, accountB, ...>> a customer may have several accounts, the field could support advance service i.e. find customer accounts
        private Long accIdInc; // accountId can apply UUID.randomUUID().toString() for some standard scenario

        public Bank(BankCode bankCode) {
            this.bankCode = bankCode;
            this.totalBalance = BigDecimal.ZERO;
            this.accounts = new HashMap<>();
            this.accIdInc = 0L;
        }

        public BankCode getBankCode() {
            return this.bankCode;
        }

        public BigDecimal getTotalBalance() {
            System.out.println("Bank " + this.bankCode + " remain total balance: $" + this.totalBalance);
            return this.totalBalance;
        }

        public BigDecimal getAccountBalance(Long accountId) {
            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }
            System.out.println("Account " + accountId + " (" + account.customerName + ") remain balance: $" + account.getBalance());
            return account.getBalance();
        }

        public BigDecimal withdraw(Long accountId, BigDecimal withdrawAmount) {
            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }
            BigDecimal accountBalance = account.getBalance();
            if (accountBalance.compareTo(withdrawAmount) < 0) {
                throw new RuntimeException("Account balance cannot be negative.");
            }

            /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
            account.setBalance(accountBalance.subtract(withdrawAmount));
            this.totalBalance = this.totalBalance.subtract(withdrawAmount);
            System.out.println("Account " + accountId + " (" + account.customerName + ") withdraw: $" + withdrawAmount);
            return withdrawAmount;
        }

        public void deposit(Long accountId, BigDecimal depositAmount) {
            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }
            if (depositAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Deposit cannot be negative.");
            }
            BigDecimal accountBalance = account.getBalance();

            /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
            account.setBalance(accountBalance.add(depositAmount));
            this.totalBalance = this.totalBalance.add(depositAmount);
            System.out.println("Account " + accountId + " (" + account.customerName + ") deposit: $" + depositAmount);
        }

        public Long createAccount(String customerName) { // consider Dependency Injection way for improvement if needed - public Long registerAccount(Account account) {...} + Account is interface + accountImpl instance created by Factory etc
            Account account = new Account(customerName);
            accounts.put(accIdInc, account);
            System.out.println("Customer " + customerName + " create an account: " + accIdInc + " in bank " + bankCode);
            return accIdInc++;
        }
    }

    static class Account {
        private String customerName;
        private BigDecimal balance;

        public Account(String customerName) {
            this.customerName = customerName;
            this.balance = BigDecimal.ZERO;
        }

        public String getCustomerName() {
            return this.customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public BigDecimal getBalance() {
            return this.balance;
        }

        public void setBalance(BigDecimal balance) {
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
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


    /**
        ./test/
     */
    public static void testSingleAccountOperations() { // test 1 test alice
        Bank anz = new Bank(BankCode.ANZ);
        Customer alice = new Customer("Alice");
        Long aliceANZAccId = anz.createAccount(alice.name); // create an account and return account id - like get a bank card

        anz.deposit(aliceANZAccId, BigDecimal.valueOf(30.0)); // simulate insert the card or id and deposit
        anz.getAccountBalance(aliceANZAccId);
        anz.getTotalBalance();

        anz.withdraw(aliceANZAccId, BigDecimal.valueOf(20.0)); // simulate insert the card and withdraw
        anz.getAccountBalance(aliceANZAccId);
        anz.getTotalBalance();

        try {
            anz.withdraw(aliceANZAccId, BigDecimal.valueOf(11.0));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testMultipleAccountsOperations() { // test 2 test multiple customers operation
        Bank asb = new Bank(BankCode.ASB);
        Customer ana = new Customer("Ana");
        Long anaASBAccId = asb.createAccount(ana.name);
        Customer bob = new Customer("Bob");
        Long bobASBAccId = asb.createAccount(bob.name);

        asb.deposit(anaASBAccId, BigDecimal.valueOf(22.0));
        asb.deposit(bobASBAccId, BigDecimal.valueOf(50.0));
        asb.getAccountBalance(anaASBAccId);
        asb.getAccountBalance(bobASBAccId);
        asb.getTotalBalance();

        asb.withdraw(bobASBAccId, BigDecimal.valueOf(10.5));
        asb.getAccountBalance(anaASBAccId);
        asb.getAccountBalance(bobASBAccId);
        asb.getTotalBalance();
    }
}
