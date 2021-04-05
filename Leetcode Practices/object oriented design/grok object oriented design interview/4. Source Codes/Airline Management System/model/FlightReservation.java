public class FlightReservation {
    private String reservationNumber;
    private FlightInstance flight;
    private Map<Passenger, FlightSeat> seatMap;
    private Date creationDate;
    private ReservationStatus status;
  
    public static FlightReservation fectchReservationDetails(String reservationNumber);
    public List<Passenger> getPassengers();
}
