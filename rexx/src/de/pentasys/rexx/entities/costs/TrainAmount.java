package de.pentasys.rexx.entities.costs;

public class TrainAmount extends Amount {

    public TrainAmount(final double amount, final Payment payment) {
        super(amount, payment, VoucherType.PUBLIC_TRANSPORT);
    }

}
