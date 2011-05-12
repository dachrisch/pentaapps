package de.pentasys.rexx.entities.costs;


public class TrainAmount extends Amount {

    public TrainAmount(double amount, Payment payment) {
        super(amount, payment, VoucherType.PUBLIC_TRANSPORT);
    }

}
