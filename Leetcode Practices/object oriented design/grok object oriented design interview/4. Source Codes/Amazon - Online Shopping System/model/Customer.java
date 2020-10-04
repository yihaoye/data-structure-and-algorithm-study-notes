public abstract class Customer {
    private ShoppingCart cart;
    private Order order;

    public ShoppingCart getShoppingCart();
    public boolean addItemToCart(Item item);
    public boolean removeItemFromCart(Item item);
}

public class Guest extends Customer {
    public boolean registerAccount();
}

public class Member extends Customer {
    private Account account;
    public OrderStatus placeOrder(Order order);
}