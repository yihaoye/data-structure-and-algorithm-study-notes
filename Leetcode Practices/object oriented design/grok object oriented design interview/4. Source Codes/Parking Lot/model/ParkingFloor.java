public class ParkingFloor {
    private String name;
    private HashMap<String, HandicappedSpot> handicappedSpots;
    private HashMap<String, CompactSpot> compactSpots;
    private HashMap<String, LargeSpot> largeSpots;
    private HashMap<String, MotorbikeSpot> motorbikeSpots;
    private HashMap<String, ElectricSpot> electricSpots;
    private HashMap<String, CustomerInfoPortal> infoPortals;
    private ParkingDisplayBoard displayBoard;
  
    public ParkingFloor(String name) {
      this.name = name;
    }
  
    public void addParkingSpot(ParkingSpot spot) {
      switch (spot.getType()) {
      case ParkingSpotType.HANDICAPPED:
        handicappedSpots.put(spot.getNumber(), spot);
        break;
      case ParkingSpotType.COMPACT:
        compactSpots.put(spot.getNumber(), spot);
        break;
      case ParkingSpotType.LARGE:
        largeSpots.put(spot.getNumber(), spot);
        break;
      case ParkingSpotType.MOTORBIKE:
        motorbikeSpots.put(spot.getNumber(), spot);
        break;
      case ParkingSpotType.ELECTRIC:
        electricSpots.put(spot.getNumber(), spot);
        break;
      default:
        print("Wrong parking spot type!");
      }
    }
  
    public void assignVehicleToSpot(Vehicle vehicle, ParkingSpot spot) {
      spot.assignVehicle(vehicle);
      switch (spot.getType()) {
      case ParkingSpotType.HANDICAPPED:
        updateDisplayBoardForHandicapped(spot);
        break;
      case ParkingSpotType.COMPACT:
        updateDisplayBoardForCompact(spot);
        break;
      case ParkingSpotType.LARGE:
        updateDisplayBoardForLarge(spot);
        break;
      case ParkingSpotType.MOTORBIKE:
        updateDisplayBoardForMotorbike(spot);
        break;
      case ParkingSpotType.ELECTRIC:
        updateDisplayBoardForElectric(spot);
        break;
      default:
        print("Wrong parking spot type!");
      }
    }
  
    private void updateDisplayBoardForHandicapped(ParkingSpot spot) {
      if (this.displayBoard.getHandicappedFreeSpot().getNumber() == spot.getNumber()) {
        // find another free handicapped parking and assign to displayBoard
        for (String key : handicappedSpots.keySet()) {
          if (handicappedSpots.get(key).isFree()) {
            this.displayBoard.setHandicappedFreeSpot(handicappedSpots.get(key));
          }
        }
        this.displayBoard.showEmptySpotNumber();
      }
    }
  
    private void updateDisplayBoardForCompact(ParkingSpot spot) {
      if (this.displayBoard.getCompactFreeSpot().getNumber() == spot.getNumber()) {
        // find another free compact parking and assign to displayBoard
        for (String key : compactSpots.keySet()) {
          if (compactSpots.get(key).isFree()) {
            this.displayBoard.setCompactFreeSpot(compactSpots.get(key));
          }
        }
        this.displayBoard.showEmptySpotNumber();
      }
    }
  
    public void freeSpot(ParkingSpot spot) {
      spot.removeVehicle();
      switch (spot.getType()) {
      case ParkingSpotType.HANDICAPPED:
        freeHandicappedSpotCount++;
        break;
      case ParkingSpotType.COMPACT:
        freeCompactSpotCount++;
        break;
      case ParkingSpotType.LARGE:
        freeLargeSpotCount++;
        break;
      case ParkingSpotType.MOTORBIKE:
        freeMotorbikeSpotCount++;
        break;
      case ParkingSpotType.ELECTRIC:
        freeElectricSpotCount++;
        break;
      default:
        print("Wrong parking spot type!");
      }
    }
}