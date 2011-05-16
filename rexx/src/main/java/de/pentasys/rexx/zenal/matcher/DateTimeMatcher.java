package de.pentasys.rexx.zenal.matcher;

import org.hamcrest.Matcher;
import org.joda.time.DateTime;

import ch.lambdaj.function.matcher.LambdaJMatcher;

import com.google.common.base.Function;

public class DateTimeMatcher extends LambdaJMatcher<DateTime> {

    public static Matcher<?> isBefore(final DateTime reference) {
        return new DateTimeMatcher(new IsBeforeDateTimeMatchFunction(reference));
    }

    public static Matcher<?> isAfter(final DateTime reference) {
        return new DateTimeMatcher(new IsAfterDateTimeMatchFunction(reference));
    }

    private final Function<DateTime, Boolean> matchFunction;

    public DateTimeMatcher(final Function<DateTime, Boolean> matchFunction) {
        this.matchFunction = matchFunction;
    }

    @Override
    public boolean matches(final Object actual) {
        return matchFunction.apply((DateTime) actual);
    }

}