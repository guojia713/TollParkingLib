package com.tollparking.api.errors;

public class TollParkingException extends Exception {

    protected TollParkingException(String message) {
        super(message);
    }

    /**
     * For the possible exceptions
     *
     * @param status
     * @param message
     * @throws CarNotFoundException
     */
    public TollParkingException(String status, String message) throws CarNotFoundException {
        if ("CAR_NOT_FOUND" .equals(status)) {
            throw new CarNotFoundException(message);
        }
    }
}
