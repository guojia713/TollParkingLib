package com.tollparking.api.errors;

public class CarNotFoundException extends TollParkingException {

    public CarNotFoundException(String carID) {
        super("ATTENTION : The car number <" + carID + " > is not in the toll parking");
    }
}
