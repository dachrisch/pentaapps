package de.pentasys.rexx.entities.costs;

public abstract class Amount {

    private final double amount;
    private final Payment payment;
    private final VoucherType voucherType;

    public Amount(final double amount, final Payment payment, final VoucherType voucherType) {
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
