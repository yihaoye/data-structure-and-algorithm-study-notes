public abstract class Person {
    private String name;
    private Address address;
    private String email;
    private String phone;
  
    private Account account;
}
  
public class Customer extends Person {
    private String frequentFlyerNumber;
  
    public List<Itinerary> getItineraries();
}
