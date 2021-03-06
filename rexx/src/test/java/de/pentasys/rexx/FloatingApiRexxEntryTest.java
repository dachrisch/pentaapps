package de.pentasys.rexx;

import static de.pentasys.builder.DateTimeGenerator.datetime;
import static de.pentasys.builder.DateTimeGenerator.from;
import static de.pentasys.rexx.builder.ExpenseBuilder.taxi;
import static de.pentasys.rexx.builder.ExpenseBuilder.train;
import static de.pentasys.rexx.builder.RexxJourneyBuilder.doJourney;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.builder.Project;
import de.pentasys.builder.TimespanDateTime;
import de.pentasys.rexx.builder.RexxJourney;
import de.pentasys.rexx.entities.RexxTrip;
import de.pentasys.rexx.entities.TripCities;
import de.pentasys.rexx.entities.expenses.Payment;
import de.pentasys.rexx.entities.expenses.VoucherType;

public class FloatingApiRexxEntryTest {

	@Test
	public void createExpenseToTrip() throws Exception {
		final DateTime tripEndDate = new DateTime();
		final DateTime tripStartDate = tripEndDate.minusHours(8);
		final RexxTrip trip = new RexxTrip(new TripCities("a", "b"), new TimespanDateTime(tripStartDate, tripEndDate),
				"bla");
		final RexxJourney rexxJourney = new RexxJourney(Project.MEDIASATURN, new TripCities("a", "b"),
				new TimespanDateTime(tripEndDate, tripEndDate), Collections.singletonList(trip));

		trip.with().inboundCosts(train(12.50, Payment.CREDIT)).outboundCosts(train(12.50, Payment.CREDIT));
		trip.with().inboundCosts(taxi(14., Payment.CASH)).outboundCosts(taxi(14., Payment.CASH));

		assertThat(rexxJourney.getTrips().size(), is(1));
		assertThat(rexxJourney.getTrips().first().getCosts().size(), is(4));
		assertThat(rexxJourney.getTrips().first().getCosts().get(0).getAmount(), is(12.5));
		assertThat(rexxJourney.getTrips().first().getCosts().get(0).getVoucherType(), is(VoucherType.PUBLIC_TRANSPORT));
		assertThat(rexxJourney.getTrips().first().getCosts().get(0).getIssueDate(), is(tripStartDate));
		assertThat(rexxJourney.getTrips().first().getCosts().get(1).getPayment(), is(Payment.CREDIT));
		assertThat(rexxJourney.getTrips().first().getCosts().get(1).getIssueDate(), is(tripEndDate));

		assertThat(rexxJourney.getTrips().first().getCosts().get(2).getAmount(), is(14.));
		assertThat(rexxJourney.getTrips().first().getCosts().get(2).getVoucherType(), is(VoucherType.TAXI));
		assertThat(rexxJourney.getTrips().first().getCosts().get(3).getPayment(), is(Payment.CASH));
	}

	@Test
	public void createInlandTrip() throws Exception {
		final RexxJourney rexxJourney = doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50))
				.from("München").till(datetime(2011, 5, 15, 17, 50)).to("Ingolstadt")
				.withTrip(from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 15, 17, 50)), "projekteinsatz");

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
	public void testname() throws Exception {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
		df.applyPattern("#,###,##0.00");
		assertThat(df.format(12.4), is("12,40"));
	}

}
