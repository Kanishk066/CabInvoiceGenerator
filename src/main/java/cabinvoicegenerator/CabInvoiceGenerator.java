package cabinvoicegenerator;

import java.util.ArrayList;
import java.util.List;

public class CabInvoiceGenerator {
     static final double COST_PER_KM = 10.0;
     static final double COST_PER_MINUTE = 1.0;
     static final double MINIMUM_FARE = 5.0;

    public double calculateFare(double distance, int minutes) {
        double totalFare = distance * COST_PER_KM + minutes * COST_PER_MINUTE;
        return Math.max(totalFare, MINIMUM_FARE);
    }
    public double calculateTotalFare(List<Ride> rides){
        double totalFare = 0.0;
        for (Ride ride : rides) {
            totalFare += calculateFare(ride.getDistance(), ride.getMinutes());
        }
        return totalFare;
    }
    public static void main(String[] args) {
        List<Ride> rides = new ArrayList<>();
        rides.add(new Ride(10.5, 20));  // Ride 1
        rides.add(new Ride(5.0, 15));   // Ride 2

        CabInvoiceGenerator invoiceGenerator = new CabInvoiceGenerator();
        double totalFare = invoiceGenerator.calculateTotalFare(rides);

        System.out.println("Aggregate Total Fare: $" + totalFare);
    }
}
class Ride {
    private double distance;
    private int minutes;

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
