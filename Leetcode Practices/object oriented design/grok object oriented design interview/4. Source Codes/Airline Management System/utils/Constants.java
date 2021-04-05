public enum FlightStatus{
    ACTIVE,
    SCHEDULED,
    DELAYED,
    DEPARTED,
    LANDED,
    IN_AIR,
    ARRIVED,
    CANCELLED,
    DIVERTED,
    UNKNOWN
}
  
public enum PaymentStatus{
    UNPAID,
    PENDING,
    COMPLETED,
    FILLED,
    DECLINED,
    CANCELLED,
    ABANDONED,
    SETTLING,
    SETTLED,
    REFUNDED
}
  
public enum ReservationStatus{
    REQUESTED,
    PENDING,
    CONFIRMED,
    CHECKED_IN,
    CANCELLED,
    ABANDONED
}
  
public enum SeatClass {
    ECONOMY,
    ECONOMY_PLUS,
    PREFERRED_ECONOMY,
    BUSINESS,
    FIRST_CLASS
}
  
public enum SeatType {
    REGULAR,
    ACCESSIBLE,
    EMERGENCY_EXIT,
    EXTRA_LEG_ROOM
}
  
public enum AccountStatus{
    ACTIVE,
    CLOSED,
    CANCELED,
    BLACKLISTED,
    BLOCKED
}
  
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
