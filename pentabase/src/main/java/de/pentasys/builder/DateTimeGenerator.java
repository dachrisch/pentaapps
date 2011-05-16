package de.pentasys.builder;

import org.joda.time.DateTime;

public class DateTimeGenerator {

    public static DateTimeGenerator from(final DateTime time) {
        return new DateTimeGenerator(time);
    }

    public static DateTime datetime(final int year, final int month, final int day, final int hour, final int minute) {
        return new DateTime(year, month, day, hour, minute, 0, 0);
    }

    public static DateTime date(final int year, final int month, final int day) {
        return new DateTime(year, month, day, 0, 0, 0, 0);
    }

    private final DateTime fromTime;

    public DateTimeGenerator(final DateTime time) {
        fromTime = time;
    }

    public TimespanDateTime till(final DateTime tillTime) {
        if (tillTime.isBefore(fromTime)) {
            throw new IllegalArgumentException(String
                    .format("till [%s] cannot be before from [%s]", tillTime, fromTime));
        }
        return new TimespanDateTime(fromTime, tillTime);
    }

    public TimespanDateTime till(final int hour, final int minute) {
        return new TimespanDateTime(fromTime, fromTime.withTime(hour, minute, 0, 0));
    }

}
