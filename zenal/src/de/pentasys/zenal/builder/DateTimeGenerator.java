package de.pentasys.zenal.builder;

import org.joda.time.DateTime;

public class DateTimeGenerator {

    public static DateTimeGenerator from(final DateTime time) {
        return new DateTimeGenerator(time);
    }

    private final DateTime fromTime;

    public DateTimeGenerator(final DateTime time) {
        fromTime = time;
    }

    public TimespanDateTime till(final DateTime tillTime) {
        return new TimespanDateTime(fromTime, tillTime);
    }

    public TimespanDateTime till(final int hour, final int minute) {
        return new TimespanDateTime(fromTime, fromTime.withTime(hour, minute, 0, 0));
    }

}
