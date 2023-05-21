package cabinvoicegenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CabInvoiceGenerator {
    static final double NORMAL_COST_PER_KM = 10.0;
    static final double NORMAL_COST_PER_MINUTE = 1.0;
    static final double NORMAL_MINIMUM_FARE = 5.0;
    static final double PREMIUM_COST_PER_KM = 15.0;
    static final double PREMIUM_COST_PER_MINUTE = 2.0;
    static final double PREMIUM_MINIMUM_FARE = 20.0;

    public double calculateFare(double distance, int minutes, RideType rideType) {
        double costPerKm = rideType == RideType.NORMAL ? NORMAL_COST_PER_KM : PREMIUM_COST_PER_KM;
        double costPerMinute = rideType == RideType.NORMAL ? NORMAL_COST_PER_MINUTE : PREMIUM_COST_PER_MINUTE;
        double minimumFare = rideType == RideType.NORMAL ? NORMAL_MINIMUM_FARE : PREMIUM_MINIMUM_FARE;

        double totalFare = distance * costPerKm + minutes * costPerMinute;
        return Math.max(totalFare, minimumFare);
    }

    public InvoiceSummary calculateInvoiceSummary(List<Ride> rides, RideType rideType) {
        double totalFare = 0.0;
        int totalRides = rides.size();

        for (Ride ride : rides) {
            totalFare += calculateFare(ride.getDistance(), ride.getMinutes(), rideType);
        }

        double averageFare = totalFare / totalRides;
        return new InvoiceSummary(totalRides, totalFare, averageFare);
    }

    public InvoiceSummary getInvoiceByUserId(int userId, RideRepository rideRepository, RideType rideType) {
        List<Ride> rides = rideRepository.getRidesByUserId(userId);
        return calculateInvoiceSummary(rides, rideType);
    }

    public static void main(String[] args) {
        // Example usage
        RideRepository rideRepository = new RideRepository();
        rideRepository.addRides(1, new Ride(10.5, 20, RideType.NORMAL));     // User 1 - Normal Ride 1
        rideRepository.addRides(1, new Ride(5.0, 15, RideType.NORMAL));      // User 1 - Normal Ride 2
        rideRepository.addRides(2, new Ride(7.0, 18, RideType.PREMIUM));     // User 2 - Premium Ride 1
        rideRepository.addRides(2, new Ride(3.5, 10, RideType.PREMIUM));     // User 2 - Premium Ride 2

        CabInvoiceGenerator invoiceGenerator = new CabInvoiceGenerator();
        int userId = 1;
        RideType rideType = RideType.NORMAL;
        InvoiceSummary invoiceSummary = invoiceGenerator.getInvoiceByUserId(userId, rideRepository, rideType);

        System.out.println("Total Number of Rides: " + invoiceSummary.getTotalRides());
        System.out.println("Total Fare: $" + invoiceSummary.getTotalFare());
        System.out.println("Average Fare per Ride: $" + invoiceSummary.getAverageFare());
    }
}

enum RideType {
    NORMAL,
    PREMIUM
}

class Ride {
    double distance;
    int minutes;
    RideType rideType;
    public Ride(double distance, int minutes, RideType rideType) {
        this.distance = distance;
        this.minutes = minutes;
        this.rideType = rideType;
    }

    public double getDistance() {
        return distance;
    }

    public int getMinutes() {
        return minutes;
    }

    public RideType getRideType() {
        return rideType;
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