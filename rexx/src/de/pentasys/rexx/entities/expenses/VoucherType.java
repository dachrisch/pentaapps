package de.pentasys.rexx.entities.expenses;

public enum VoucherType {
    PUBLIC_TRANSPORT("Öffentliche Verkehrsmittel über 50 KM"), TAXI("Taxi bis 50 km");

    private final String voucherType;

    VoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }

}
