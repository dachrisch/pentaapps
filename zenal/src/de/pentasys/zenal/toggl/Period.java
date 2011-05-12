package de.pentasys.zenal.toggl;

public enum Period {
    THIS_WEEK("This week");

    private final String period;

    private Period(final String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }
}
