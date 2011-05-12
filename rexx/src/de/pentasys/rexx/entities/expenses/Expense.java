package de.pentasys.rexx.entities.expenses;

import org.joda.time.DateTime;

public abstract class Expense {

    private final double amount;
    private final Payment payment;
    private final VoucherType voucherType;
    private DateTime issueDate;

    public Expense(final double amount, final Payment payment, final VoucherType voucherType) {
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

    public DateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final DateTime issueDate) {
        this.issueDate = issueDate;
    }

}
