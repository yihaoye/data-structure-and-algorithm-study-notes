public abstract class Payment {
    private PaymentStatus status;
    private double amount;

    public PaymentStatus processPayment();
}

public class CreditCardTransaction extends Payment {
}

public class ElectronicBankTransaction extends Payment {
}

public class CreditCard {
    private String nameOnCard;
    private String cardNumber;
    private int code;
    private Address billingAddress;
}

public class ElectronicBankTransfer {
    private String bankName;
    private String routingNumber;
    private String accountNumber;
}
