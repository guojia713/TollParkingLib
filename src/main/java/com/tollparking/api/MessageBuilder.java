package com.tollparking.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class of MessageBuilder
 * @author jiaguo
 */
public class MessageBuilder {

    public static String buildCarParkMessage(int carID, String carType, int parkingSlotId, LocalDateTime arrivalTime) {

        StringBuilder message = new StringBuilder();
        message.append("===> Car number < " + carID + " >")
                .append(" of type " + carType)
                .append(" has been parked on the slot number < " + parkingSlotId + " >")
                .append(" for " + carType)
                .append(" at : ")
                .append(arrivalTime);

        return message.toString();
    }

    public static String buildCarLeaveMessage(int carID, String carType, int parkingSlotId, long duration, BigDecimal parkingFee, LocalDateTime departureTime) {

        StringBuilder message = new StringBuilder();
        message.append("<=== Car number < " + carID + " >")
                .append(" of type " + carType)
                .append(" has left the parking slot number < " + parkingSlotId + " >")
                .append(" type " + carType)
                .append(" at : " + departureTime + "\n")
                .append("     It stayed " + duration + " hours in the parking")
                .append(" and the bill is " + parkingFee + " dollars")
                .append("\n^^^ Parking slot number < " + parkingSlotId + " >")
                .append(" for " + carType)
                .append(" is free now ^^^");
        return message.toString();
    }

    public static String buildCarRefusedByParkingMessage(int carID, String carType, LocalDateTime arrivalTime) {
        StringBuilder message = new StringBuilder();
        message.append("===> Sorry, you're too late !! No parking slot is available for car number < " + carID + " >")
                .append(" of type " + carType)
                .append(" at : " + arrivalTime);
        return message.toString();
    }
}
