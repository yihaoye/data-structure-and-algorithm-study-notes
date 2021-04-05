public class ATM {
    private int atmID;
    private Address location;
  
    private CashDispenser cashDispenser;
    private Keypad keypad;
    private Screen screen;
    private Printer printer;
    private CheckDeposit checkDeposit;
    private CashDeposit cashDeposit;
  
    public boolean authenticateUser();
    public boolean makeTransaction(Customer customer, Transactiopn transaction);
}
