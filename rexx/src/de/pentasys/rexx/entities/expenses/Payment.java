package de.pentasys.rexx.entities.expenses;

public enum Payment {
    CREDIT("Firmenkreditkarte"), CASH("Bar");

    private final String payment;

    Payment(final String payment) {
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }
}
