public class Flight {
    private String flightNumber;
    private Airport departure;
    private Airport arrival;
    private int durationInMinutes;
  
    private List<WeeklySchedules> weeklySchedules;
    private List<CustomSchedules> customSchedules;
    private List<FlightInstance> flightInstances;
}
