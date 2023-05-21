package cabinvoicegenerator;

public class CabInvoiceGenerator {
     static final double COST_PER_KM = 10.0;
     static final double COST_PER_MINUTE = 1.0;
     static final double MINIMUM_FARE = 5.0;

    public double calculateFare(double distance, int minutes) {
        double totalFare = distance * COST_PER_KM + minutes * COST_PER_MINUTE;
        return Math.max(totalFare, MINIMUM_FARE);
    }
    public static void main(String[] args) {
        double distance = 10.5;  // Distance in kilometers
        int minutes = 20;  // Time in minutes

        CabInvoiceGenerator invoiceGenerator = new CabInvoiceGenerator();
        double fare = invoiceGenerator.calculateFare(distance, minutes);

        System.out.println("Total Fare: $" + fare);

    }
}
