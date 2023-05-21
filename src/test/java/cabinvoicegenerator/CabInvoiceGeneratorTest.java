package cabinvoicegenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CabInvoiceGeneratorTest {
    CabInvoiceGenerator invoiceGenerator;
    RideRepository rideRepository;

    @Before
    public void setup() {
        invoiceGenerator = new CabInvoiceGenerator();
        rideRepository = new RideRepository();
    }

    @Test
    public void shouldCalculateFareForNormalRide() {
        Ride ride = new Ride(10.5, 20, RideType.NORMAL);
        double fare = invoiceGenerator.calculateFare(ride.getDistance(), ride.getMinutes(), ride.getRideType());
        Assert.assertEquals(115.0, fare, 0.001);
    }

    @Test
    public void shouldCalculateFareForPremiumRide() {
        Ride ride = new Ride(7.0, 18, RideType.PREMIUM);
        double fare = invoiceGenerator.calculateFare(ride.getDistance(), ride.getMinutes(), ride.getRideType());
        Assert.assertEquals(180.0, fare, 0.001);
    }

    @Test
    public void shouldCalculateInvoiceSummaryForUser() {
        rideRepository.addRides(1, new Ride(10.5, 20, RideType.NORMAL));
        rideRepository.addRides(1, new Ride(5.0, 15, RideType.NORMAL));
        rideRepository.addRides(1, new Ride(7.0, 18, RideType.PREMIUM));
        rideRepository.addRides(1, new Ride(3.5, 10, RideType.PREMIUM));

        InvoiceSummary invoiceSummary = invoiceGenerator.getInvoiceByUserId(1, rideRepository, RideType.NORMAL);

        Assert.assertEquals(2, invoiceSummary.getTotalRides());
        Assert.assertEquals(75.0, invoiceSummary.getTotalFare(), 0.001);
        Assert.assertEquals(37.5, invoiceSummary.getAverageFare(), 0.001);
    }

}
