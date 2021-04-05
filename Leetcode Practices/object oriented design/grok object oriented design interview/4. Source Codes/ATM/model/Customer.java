public class Customer {
    private String name;
    private String email;
    private String phone;
    private Address address;
    private CustomerStatus status;
  
    private Card card;
    private Account account;
  
    public boolean makeTransaction(Transactiopn transaction);
    public Address getBillingAddress();
}
