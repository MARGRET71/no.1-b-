import java.util.ArrayList;
import java.util.List;

public class Company {
    public List<Vehicle> vehicles;
    private int lostFaresCount;

    public Company() {
        this.vehicles = new ArrayList<>()
        addVehicle(new Vehicle("Taxi-001", 4));
        addVehicle(new Vehicle("Shuttle-001", 10));
        addVehicle(new Vehicle("Taxi-002", 4));
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public boolean scheduleVehicle(Passenger passenger) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() && vehicle.getCapacity() >= passenger.getRequiredSeats()) {
                vehicle.setAvailable(false); // Mark as busy
                System.out.println("Scheduled " + vehicle.getId() + " for passenger " + passenger.getName() +
                                   " from " + passenger.getPickupLocation() + " to " + passenger.getDestinationLocation());
                return true;
            }
        }
        lostFaresCount++;
        System.out.println("No vehicle available for passenger " + passenger.getName() + ". Request lost.");
        return false;
    }

    public int getLostFaresCount() {
        return lostFaresCount;
    }

    public void notifyPickupArrival(String vehicleId, Location pickupLocation) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                vehicle.setCurrentLocation(pickupLocation);
                vehicle.pickupPassengers();
                System.out.println("Driver of " + vehicleId + " arrived at pickup location " + pickupLocation);
                return;
            }
        }
        System.out.println("Vehicle " + vehicleId + " not found.");
    }

    public void notifyDropOff(String vehicleId, Location destinationLocation) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                vehicle.dropOffPassengers(destinationLocation);
                System.out.println("Driver of " + vehicleId + " completed drop-off at " + destinationLocation);
                return;
            }
        }
        System.out.println("Vehicle " + vehicleId + " not found.");
    }

    public void printVehicleStatus() {
        System.out.println("\n--- Vehicle Status ---");
        for (Vehicle vehicle : vehicles) {
            System.out.println("Vehicle ID: " + vehicle.getId() +
                               ", Capacity: " + vehicle.getCapacity() +
                               ", Available: " + vehicle.isAvailable() +
                               ", Current Location: " + vehicle.getCurrentLocation());
        }
        System.out.println("Lost Fares: " + lostFaresCount);
        System.out.println("----------------------");
    }

    // Helper for JUnit testing
    public int getAvailableVehicleCount() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    // Helper for JUnit testing
    public void resetVehiclesAvailability() {
        for (Vehicle vehicle : vehicles) {
            vehicle.setAvailable(true);
        }
      
    }

    // Helper for JUnit testing
    public Vehicle getVehicleById(String id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }
}
