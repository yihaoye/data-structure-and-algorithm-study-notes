public abstract class Transaction {
    private int transactionId;
    private Date creationTime;
    private TransactionStatus status;
    public boolean makeTransation();
}
  
public class BalanceInquiry extends Transaction {
    private int accountId;
    public double getAccountId();
}

public class Withdraw extends Transaction {
    private double amount;
    public double getAmount();
}
  
public class Transfer extends Transaction {
    private int destinationAccountNumber;
    public int getDestinationAccount();
}
  
public abstract class Deposit extends Transaction {
    private double amount;
    public double getAmount();
}

public class CheckDeposit extends Deposit {
    private String checkNumber;
    private String bankCode;
  
    public String getCheckNumber();
}
  
public class CashDeposit extends Deposit {
    private double cashDepositLimit;
}
