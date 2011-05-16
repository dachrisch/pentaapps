package de.pentasys.rexx.zenal.matcher;

import org.joda.time.DateTime;

import com.google.common.base.Function;

public class IsAfterDateTimeMatchFunction implements Function<DateTime, Boolean> {
    private final DateTime reference;

    public IsAfterDateTimeMatchFunction(final DateTime reference) {
        this.reference = reference;
    }

    @Override
    public Boolean apply(final DateTime input) {
        return input.isAfter(reference.minusMillis(1));
    }

}
