package de.pentasys.rexx.update;

import static de.pentasys.rexx.builder.CostBuilder.train;
import static de.pentasys.rexx.builder.RexxJourneyBuilder.doJourney;
import static de.pentasys.zenal.builder.DateTimeGenerator.from;
import static de.pentasys.zenal.builder.ZenalEntryCreator.datetime;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.rexx.entities.RexxJourney;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.rexx.entities.costs.VoucherType;
import de.pentasys.zenal.builder.Project;
import de.pentasys.zenal.builder.TimespanDateTime;

public class FloatingApiRexxEntryTest {

    @Test
    public void createInlandTrip() throws Exception {
        RexxJourney rexxJourney = doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50)).from("München")
                .till(datetime(2011, 5, 15, 17, 50)).to("Ingolstadt");
        rexxJourney.doTrip(from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 15, 17, 50)), "projekteinsatz");

        assertThat(rexxJourney.getLeavingCity(), is("München"));
        assertThat(rexxJourney.getArrivalCity(), is("Ingolstadt"));

        assertThat(rexxJourney.getJourneyFrom(), is(datetime(2011, 5, 5, 7, 50)));
        assertThat(rexxJourney.getJourneyTill(), is(datetime(2011, 5, 15, 17, 50)));

        assertThat(rexxJourney.getProject(), is(Project.MEDIASATURN));

        assertThat(rexxJourney.getTrips().size(), is(1));
        assertThat(rexxJourney.getTrips().first().getFrom(), is(datetime(2011, 5, 5, 7, 50)));
        assertThat(rexxJourney.getTrips().first().getTill(), is(datetime(2011, 5, 15, 17, 50)));
        assertThat(rexxJourney.getTrips().first().getReason(), is("projekteinsatz"));
    }

    @Test
    public void createExpenseToTrip() throws Exception {
        RexxJourney rexxJourney = new RexxJourney(Project.MEDIASATURN, new TripCities("a", "b"), new TimespanDateTime(
                new DateTime(), new DateTime()));
        rexxJourney.doTrip(new TimespanDateTime(new DateTime().minusHours(8), new DateTime()), "bla").with()
                .inboundCosts(train(12.50, Payment.CREDIT)).outboundCosts(train(12.50, Payment.CREDIT));

        assertThat(rexxJourney.getTrips().size(), is(1));
        assertThat(rexxJourney.getTrips().first().getCosts().size(), is(2));
        assertThat(rexxJourney.getTrips().first().getCosts().get(0).getAmount(), is(12.5));
        assertThat(rexxJourney.getTrips().first().getCosts().get(0).getVoucherType(), is(VoucherType.PUBLIC_TRANSPORT));
        assertThat(rexxJourney.getTrips().first().getCosts().get(1).getPayment(), is(Payment.CREDIT));
    }

}
