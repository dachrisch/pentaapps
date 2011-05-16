package de.pentasys.rexx.zenal.matcher;

import org.joda.time.DateTime;

import com.google.common.base.Function;

class IsBeforeDateTimeMatchFunction implements Function<DateTime, Boolean> {
    private final DateTime reference;

    public IsBeforeDateTimeMatchFunction(final DateTime reference) {
        this.reference = reference;
    }

    @Override
    public Boolean apply(final DateTime input) {
        return input.isBefore(reference.plusMillis(1));
    }
}