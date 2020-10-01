public abstract class ParkingSpot {
    private String number;
    private boolean free;
    private Vehicle vehicle;
    private final ParkingSpotType type;
  
    public boolean IsFree();
  
    public ParkingSpot(ParkingSpotType type) {
      this.type = type;
    }
  
    public boolean assignVehicle(Vehicle vehicle) {
      this.vehicle = vehicle;
      free = false;
    }
  
    public boolean removeVehicle() {
      this.vehicle = null;
      free = true;
    }
}
  
public class HandicappedSpot extends ParkingSpot {
    public HandicappedSpot() {
        super(ParkingSpotType.HANDICAPPED);
    }
}
  
public class CompactSpot extends ParkingSpot {
    public CompactSpot() {
        super(ParkingSpotType.COMPACT);
    }
}
  
public class LargeSpot extends ParkingSpot {
    public LargeSpot() {
        super(ParkingSpotType.LARGE);
    }
}
  
public class MotorbikeSpot extends ParkingSpot {
    public MotorbikeSpot() {
        super(ParkingSpotType.MOTORBIKE);
    }
}
  
public class ElectricSpot extends ParkingSpot {
    public ElectricSpot() {
        super(ParkingSpotType.ELECTRIC);
    }
}