public class Seat {
    private String seatNumber;
    private SeatType type;
    private SeatClass _class;
}
  
public class FlightSeat extends Seat {
    private double fare;
    public double getFare();
}
