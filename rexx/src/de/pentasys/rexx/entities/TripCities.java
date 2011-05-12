package de.pentasys.rexx.entities;

public class TripCities {

    private final String leavingCity;
    private final String arrivalCity;

    public String getLeavingCity() {
        return leavingCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public TripCities(String leavingCity, String arrivalCity) {
        this.leavingCity = leavingCity;
        this.arrivalCity = arrivalCity;
    }

}
