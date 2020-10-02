public abstract class Vehicle {
    private String licenseNumber;
    private final VehicleType type;
    private ParkingTicket ticket;
  
    public Vehicle(VehicleType type) {
      this.type = type;
    }
  
    public void assignTicket(ParkingTicket ticket) {
      this.ticket = ticket;
    }
}
  
public class Car extends Vehicle {
    public Car() {
      super(VehicleType.CAR);
    }
}
  
public class Van extends Vehicle {
    public Van() {
      super(VehicleType.VAN);
    }
}
  
public class Truck extends Vehicle {
    public Truck() {
      super(VehicleType.TRUCK);
    }
}
  
// Similarly we can define classes for Motorcycle and Electric vehicles