// 需求分析：
// Parking Lot (Several Floor - when car enter, tell car to the floor)
// any size car can be put (several size Parking Spot for different size car: small, middle, large), smaller car can be put into larger spot, but not vice versa, always try to get smaller to smaller first
// if not available position left for the car size, should tell them no position error

// ticket should generated for each car with start time and end time, different car cost different payment ratio（应该在一开始问清是按 car size 还是最终停靠的 spot size 算钱）
// should consider a discount as well.

// 是否应该在下面的代码定义类等等之前先画 UML？
// 考虑代码是否可以适应更多 follow up 的易扩展性？

// 测试？

public class Solution {
    public static void main(String[] args) {
        // ParkingLot get instance (singleton) and init
        // new Car() ... and use ParkingLot start processing
        // test ...
    }
}

class ParkingLot {
	Map<Integer, ParkingFloor> floorMap; // 注释添加 Key 是什么意思，如下。floorMap 的 key 是否应该有顺序？
    Map<String, Double> priceMap; // <ParkingSpot Size, Ratio of the cost of the size> 一对多？多对一？一对一？多对多？策略模式如何实现？维护多个 Map 不是好的方式（实际工作中也不太会这样做），还是更多地考虑如何使用设计模式（比如工厂模式）消除可能存在的瓶颈（可以初略实现或提及其框架）

        public Ticket enter(Car car, Timestamp start) { // 考虑返回数据类型，返回 state 是否更好
            ParkingSpot parkingSpot = checkSpot(car); // iterate floorMap, each floorMap check if there is ParkingSpot suitable for the car size and available then we assign it to the car（另外两个差不多的 size 的车同时两个入口进入，如何避免分配重复 spot？比如使用 synchronized 锁）（此处暴力解了，如何更好地实现 - 考虑一些算法）
            if (parkingSpot == null) {
                System.out.println(“not position left for: ” + car.size); // exception 处理可以改进，考虑更多意外情况
                return null;
            }
            return createTicket(parkingSpot, start); // 这里间接地返回 floor，是否有更好地实现
        }

        public Double exit(Ticket ticket, Timestamp end) {
            Double cost = calculateFee(ticket.start, end, ticket.parkingSpot.size); // use priceMap too
            realseParkingSpot(ticket.parkingSpot);
            return cost;
        }
}

class ParkingFloor {
	Map<Integer, ParkingSpot> spotMap; // 注释添加 Key 是什么意思（车位号）
}

class ParkingSpot {
	String size; // 其实可以采用枚举
	boolean available;
	ParkingFloor parkingFloor;

	public ParkingSpot(String size) {
		this.size = size;
        this.available = true;
    }
}

class Ticket {
	Timestamp start;
	Timestamp end;
    ParkingSpot parkingSpot;

	public Ticket () {
    }
}

class Car { // 可以用 interface
	String size; // 同上 ParkingSpot，其实可以采用枚举
    public Car(String size) {
            this.size = size;
    }

    // public calculate() {

    // }
}
