public abstract class Person {
    private String name;
    private Address address;
    private String email;
    private String phone;
  
    private Account account;
}
  
public class Customer extends Person {
    public boolean makeBooking(Booking booking);
    public List<Booking> getBookings();
}
  
public class Admin extends Person {
    public boolean addMovie(Movie movie);
    public boolean addShow(Show show);
    public boolean blockUser(Customer customer);
}
  
public class FrontDeskOfficer extends Person {
    public boolean createBooking(Booking booking);
}
