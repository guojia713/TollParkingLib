package com.tollparking.api.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class of ParkingSlot
 * @author jiaguo
 */
public class ParkingSlot {

    private static final AtomicInteger count = new AtomicInteger(0); //Count helps to increment parking slot ID

    private int parkingSlotID; // Parking slot ID

    private boolean isOccupied; // Indicator occupation of the parking slot

    private CarTypeEnum authoredCarType; //Authorized car type of this parking slot

    /**
     * Constructor
     */
    public ParkingSlot(){
       this.parkingSlotID = count.incrementAndGet();
    }

    /**
     * Get Parking slot ID
     * @return int Parking Slot ID
     */
    public int getParkingSlotID() {
        return parkingSlotID;
    }

    /**
     * Get the indicator of occupation of thr parking slot
     * @return  True : this parking slot is occupied
     *          False : this parking slot is free
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Set the indicator of occupation of the parking slot
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Set Authored Car Type
     * @param authoredCarType
     */
    public void setAuthoredCarType(CarTypeEnum authoredCarType) {
        this.authoredCarType = authoredCarType;
    }
}
