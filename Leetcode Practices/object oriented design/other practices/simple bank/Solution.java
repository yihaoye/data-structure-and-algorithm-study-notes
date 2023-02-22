import java.util.*;
import java.math.*;

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
        Long customerAANZAccId = anz.createAccount(customerA.name); // create an account and return account id - like get a bank card

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
        Long customerBASBAccId = asb.createAccount(customerB.name);
        Customer customerC = new Customer(CUSTOMER_C);
        Long customerCASBAccId = asb.createAccount(customerC.name);

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
            if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Withdraw amount should be positive.");
            }

            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }

            BigDecimal accountBalance = account.getBalance();
            if (accountBalance.compareTo(withdrawAmount) < 0) {
                throw new RuntimeException("Account balance insufficient to withdraw.");
            }

            /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
            account.setBalance(accountBalance.subtract(withdrawAmount));
            this.totalBalance = this.totalBalance.subtract(withdrawAmount); // withdraw operation only check Account balance but not bank total balance since if Account balance is always not negative then bank total balance will not be negative.
            System.out.println("Account " + accountId + " (" + account.customerName + ") withdraw: $" + withdrawAmount);
            return withdrawAmount;
        }

        public void deposit(Long accountId, BigDecimal depositAmount) {
            if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Deposit amount should be positive.");
            }

            Account account = accounts.get(accountId);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }

            BigDecimal accountBalance = account.getBalance();
            /* could apply synchronized(this) block to implement atomic operation for next 2 operations if needed */
            account.setBalance(accountBalance.add(depositAmount));
            this.totalBalance = this.totalBalance.add(depositAmount);
            System.out.println("Account " + accountId + " (" + account.customerName + ") deposit: $" + depositAmount);
        }

        public Long createAccount(String customerName) { // apply CustomerDTO for improvement or consider Dependency Injection way for improvement if needed - public Long registerAccount(Account account) {...} + Account is interface + accountImpl instance created by Factory etc
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
}
