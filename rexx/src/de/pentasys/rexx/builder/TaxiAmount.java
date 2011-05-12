package de.pentasys.rexx.builder;

import de.pentasys.rexx.entities.costs.Amount;
import de.pentasys.rexx.entities.costs.Payment;
import de.pentasys.rexx.entities.costs.VoucherType;

public class TaxiAmount extends Amount {

    public TaxiAmount(final double amount, final Payment payment) {
        super(amount, payment, VoucherType.TAXI);
    }

}
