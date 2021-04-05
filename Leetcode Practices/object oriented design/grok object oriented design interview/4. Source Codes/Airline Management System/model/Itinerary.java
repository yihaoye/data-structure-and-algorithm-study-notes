public class Itinerary {
    private String customerId;
    private Airport startingAirport;
    private Airport finalAirport;
    private Date creationDate;
    private List<FlightReservation> reservations;
  
    public List<FlightReservation> getReservations();
    public boolean makeReservation();
    public boolean makePayment();
}
