public abstract class DepositSlot {
    private double totalAmount;
    public double getTotalAmount();
}
  
public class CheckDepositSlot extends DepositSlot {
    public double getCheckAmount();
}
  
public class CashDepositSlot extends DepositSlot {
    public double receiveDollarBill();
}
