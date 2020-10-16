public class Booking {
    private String bookingNumber;
    private int numberOfSeats;
    private Date createdOn;
    private BookingStatus status;
  
    private Show show;
    private List<ShowSeat> seats;
    private Payment payment;
  
    public boolean makePayment(Payment payment);
    public boolean cancel();
    public boolean assignSeats(List<ShowSeat> seats);
}
