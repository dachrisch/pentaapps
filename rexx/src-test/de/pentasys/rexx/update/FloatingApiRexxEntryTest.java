package de.pentasys.rexx.update;

import static de.pentasys.zenal.builder.DateTimeGenerator.from;
import static de.pentasys.zenal.builder.ZenalEntryCreator.datetime;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.pentasys.rexx.entities.RexxJourney;
import static de.pentasys.rexx.builder.RexxJourneyBuilder.doJourney;
import de.pentasys.zenal.builder.Project;

public class FloatingApiRexxEntryTest {

    @Test
    public void createInlandTrip() throws Exception {
        RexxJourney rexxJourney = doJourney(Project.MEDIASATURN).starting(datetime(2011, 5, 5, 7, 50)).from("München")
                .till(datetime(2011, 5, 15, 17, 50)).to("Ingolstadt")
                .doTrip(from(datetime(2011, 5, 5, 7, 50)).till(datetime(2011, 5, 15, 17, 50)), "projekteinsatz");
        

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


}
