package cabinvoicegenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CabInvoiceGenerator {
     static final double COST_PER_KM = 10.0;
     static final double COST_PER_MINUTE = 1.0;
     static final double MINIMUM_FARE = 5.0;

    public double calculateFare(double distance, int minutes) {
        double totalFare = distance * COST_PER_KM + minutes * COST_PER_MINUTE;
        return Math.max(totalFare, MINIMUM_FARE);
    }

    public InvoiceSummary calculateInvoiceSummary(List<Ride> rides) {
        double totalFare = 0.0;
        int totalRides = rides.size();

        for (Ride ride : rides) {
            totalFare += calculateFare(ride.getDistance(), ride.getMinutes());
        }

        double averageFare = totalFare / totalRides;
        return new InvoiceSummary(totalRides, totalFare, averageFare);
    }
    public InvoiceSummary getInvoiceByUserId(int userId, RideRepository rideRepository) {
        List<Ride> rides = rideRepository.getRidesByUserId(userId);
        return calculateInvoiceSummary(rides);
    }

    public static void main(String[] args) {
        RideRepository rideRepository = new RideRepository();
        rideRepository.addRides(1, new Ride(10.5, 20));  // User 1 - Ride 1
        rideRepository.addRides(1, new Ride(5.0, 15));   // User 1 - Ride 2
        rideRepository.addRides(2, new Ride(7.0, 18));   // User 2 - Ride 1

        CabInvoiceGenerator invoiceGenerator = new CabInvoiceGenerator();
        int userId = 1;
        InvoiceSummary invoiceSummary = invoiceGenerator.getInvoiceByUserId(userId, rideRepository);

        System.out.println("Total Number of Rides: " + invoiceSummary.getTotalRides());
        System.out.println("Total Fare: $" + invoiceSummary.getTotalFare());
        System.out.println("Average Fare per Ride: $" + invoiceSummary.getAverageFare());
    }
}

class Ride {
     double distance;
     int minutes;

    public Ride(double distance, int minutes) {
        this.distance = distance;
        this.minutes = minutes;
    }

    public double getDistance() {
        return distance;
    }

    public int getMinutes() {
        return minutes;
    }
}
class InvoiceSummary {
    int totalRides;
    double totalFare;
    double averageFare;

    public InvoiceSummary(int totalRides, double totalFare, double averageFare) {
        this.totalRides = totalRides;
        this.totalFare = totalFare;
        this.averageFare = averageFare;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public double getAverageFare() {
        return averageFare;
    }
}
class RideRepository {
    final List<Ride> rideList;
    // Map to store rides for each user
    final Map<Integer, List<Ride>> userRides;

    public RideRepository() {
        rideList = new ArrayList<>();
        userRides = new HashMap<>();
    }

    public void addRides(int userId, Ride ride) {
        rideList.add(ride);

        if (!userRides.containsKey(userId)) {
            userRides.put(userId, new ArrayList<>());
        }
        userRides.get(userId).add(ride);
    }

    public List<Ride> getRidesByUserId(int userId) {
        return userRides.getOrDefault(userId, new ArrayList<>());
    }
}