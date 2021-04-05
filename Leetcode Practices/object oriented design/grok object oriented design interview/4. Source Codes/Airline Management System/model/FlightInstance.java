public class FlightInstance {
    private Date departureTime;
    private String gate;
    private FlightStatus status;
    private Aircraft aircraft;
  
    public bool cancel();
    public void updateStatus(FlightStatus status);
}
  