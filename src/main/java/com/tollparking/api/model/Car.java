package com.tollparking.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class of Car
 * @author jiaguo
 */
public class Car {

    private Integer carID; //ID of the car

    private CarTypeEnum carType; //Type of the car

    private LocalDateTime arrivalTime; //The arrival time in the toll parking

    private boolean isParked; //Indicator if this car is parked

    private LocalDateTime departureTime; //The departure time from the toll parking

    private BigDecimal parkingFee; //The amount of the parking fee

    private Integer parkingSlotID; // The Id of the parking slot when parked

    /**
     * Constructor
     * Set car type and init isParked
     * @param carType
     */
    public Car(int carID, CarTypeEnum carType){
        this.carType = carType;
        this.setCarID(carID);
        this.setParked(false);
    }

    /**
     * Get Car ID
     * @return Integer carID
     */
    public Integer getCarID() {
        return carID;
    }

    /**
     * Set car ID
     * @param carID carID
     */
    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    /**
     * Get carType
     * @return CarTypeEnum carType
     */
    public CarTypeEnum getCarType() {
        return carType;
    }

    /**
     * Get arrivalTime of the car
     * @return LocalDateTime arrivalTime
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Set arrivalTime of the car
     * @param arrivalTime arrivalTime
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Get indicator if the has parked
     * @return boolean isParked
     */
    public boolean isParked() {
        return isParked;
    }

    /**
     * Set indicator if the has parked
     * @param parked
     */
    public void setParked(boolean parked) {
        isParked = parked;
    }

    /**
     * Get departureTime of the car
     * @return LocalDateTime departureTime
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Set departureTime of the car
     * @param departureTime
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Get the amount of the parking fee
     * @return BigDecimal parkingFee
     */
    public BigDecimal getParkingFee() {
        return parkingFee;
    }

    /**
     * Set the amount of the parking fee
     * @param parkingFee
     */
    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }

    /**
     * Get the Id of the parking slot
     * @return parkingSlotID
     */
    public Integer getParkingSlotID() {
        return parkingSlotID;
    }

    /**
     * Set the Id of the parking slot
     * @param parkingSlotID Id of the parking slot
     */
    public void setParkingSlotID(Integer parkingSlotID) {
        this.parkingSlotID = parkingSlotID;
    }
}

