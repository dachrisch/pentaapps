package de.pentasys.rexx.entities.expenses;

public class TaxiExpense extends Expense {

    public TaxiExpense(final double amount, final Payment payment) {
        super(amount, payment, VoucherType.TAXI);
    }

}
