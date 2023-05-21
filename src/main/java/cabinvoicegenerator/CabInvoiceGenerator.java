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

    public InvoiceSummary calculateInvoiceSummary(List<Ride> rides) {
        double totalFare = 0.0;
        int totalRides = rides.size();

        for (Ride ride : rides) {
            totalFare += calculateFare(ride.getDistance(), ride.getMinutes());
        }

        double averageFare = totalFare / totalRides;
        return new InvoiceSummary(totalRides, totalFare, averageFare);
    }

    public static void main(String[] args) {
        List<Ride> rides = new ArrayList<>();
        rides.add(new Ride(10.5, 20));  // Ride 1
        rides.add(new Ride(5.0, 15));   // Ride 2

        CabInvoiceGenerator invoiceGenerator = new CabInvoiceGenerator();
        InvoiceSummary invoiceSummary = invoiceGenerator.calculateInvoiceSummary(rides);

        System.out.println("Total Number of Rides: " + invoiceSummary.getTotalRides());
        System.out.println("Total Fare: $" + invoiceSummary.getTotalFare());
        System.out.println("Average Fare per Ride: $" + invoiceSummary.getAverageFare());
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
class InvoiceSummary {
    private int totalRides;
    private double totalFare;
    private double averageFare;

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
