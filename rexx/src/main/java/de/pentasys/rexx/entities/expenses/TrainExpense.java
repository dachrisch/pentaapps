package de.pentasys.rexx.entities.expenses;

public class TrainExpense extends Expense {

    public TrainExpense(final double amount, final Payment payment) {
        super(amount, payment, VoucherType.PUBLIC_TRANSPORT);
    }

}
