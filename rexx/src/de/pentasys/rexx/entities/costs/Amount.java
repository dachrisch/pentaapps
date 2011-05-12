package de.pentasys.rexx.entities.costs;

import de.pentasys.rexx.update.Payment;

public abstract class Amount {

    private final double amount;
    private final Payment payment;
    private final VoucherType voucherType;

    public Amount(double amount, Payment payment, VoucherType voucherType) {
        this.amount = amount;
        this.payment = payment;
        this.voucherType = voucherType;
    }

    public Double getAmount() {
        return amount;
    }

    public Payment getPayment() {
        return payment;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

}