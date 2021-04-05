public class Account {
    private int accountNumber;
    private double totalBalance;
    private double availableBalance;
  
    public double getAvailableBalance();
}
  
public class SavingAccount extends Account {
    private double withdrawLimit;
}
  
public class CheckingAccount extends Account {
    private String debitCardNumber;
}
