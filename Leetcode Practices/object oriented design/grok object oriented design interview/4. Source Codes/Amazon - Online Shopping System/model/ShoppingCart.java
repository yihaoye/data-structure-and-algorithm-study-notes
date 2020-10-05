public class ShoppingCart {
    private List<Item> items;

    public boolean addItem(Item item);
    public boolean removeItem(Item item);
    public boolean updateItemQuantity(Item item, long quantity);
    public List<Item> getItems();
    public boolean checkout();
}