public class Order {
    private String orderNumber;
    private OrderStatus status;
    private Date orderDate;
    private List<OrderLog> orderLog;

    public boolean sendForShipment();
    public boolean makePayment(Payment payment);
    public boolean addOrderLog(OrderLog orderLog);
}