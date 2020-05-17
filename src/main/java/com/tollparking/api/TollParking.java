package com.tollparking.api;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tollparking.api.errors.TollParkingException;
import com.tollparking.api.model.*;

/**
 * The main class of this Toll Parking API
 *
 * @author jiaguo
 */
public class TollParking {

    private Logger logger = Logger.getLogger(TollParking.class.getName());

    /**
     * When a car check in, check if there is available slot of the right type
     * Then park the car and log the parking details.
     * Otherwise the car is refused by the parking.
     *
     * @param car
     * @return
     * @throws InterruptedException
     */
    public Boolean carCheckIn(Parking parking, Car car) throws InterruptedException {

        // Get the list of the parking slot by car type
        List<ParkingSlot> parkingSlotsByType = parking.getParkingSlotsMap().get(car.getCarType());

        int parkingSlotId = findAvailableSlot(parkingSlotsByType, car);

        if (parkingSlotId == -1) {
            logger.log(Level.INFO, MessageBuilder.buildCarRefusedByParkingMessage(car.getCarID(), car.getCarType().name(), car.getArrivalTime()));
            return false;
        } else {
            car.setParked(true);
            car.setParkingSlotID(parkingSlotId);
            logger.log(Level.INFO, MessageBuilder.buildCarParkMessage(car.getCarID(), car.getCarType().name(), parkingSlotId, car.getArrivalTime()));
            return true;
        }
    }

    /**
     * Method to find an available parking slot
     *
     * @return
     */
    synchronized int findAvailableSlot(List<ParkingSlot> parkingSlotsByType, Car car) throws InterruptedException {

        // Find one available parking slot
        Optional<ParkingSlot> parkingSlotAvailableOpt = parkingSlotsByType
                .stream()
                .filter(p -> p.isOccupied() == false)
                .findFirst();

        // Set the arrival time for each car even if no slot available
        car.setArrivalTime(LocalDateTime.now());

        // Return -1 if no parking slot available for this type of car
        if (!parkingSlotAvailableOpt.isPresent()) {
            return -1;
        }

        // Find a parking slot available and flag occupied
        ParkingSlot parkingSlotAvailable = parkingSlotAvailableOpt.get();
        parkingSlotAvailable.setOccupied(true);
        return parkingSlotAvailable.getParkingSlotID();
    }

    /**
     * When a car leaving, parking slot becomes free and the customer pay the bill
     *
     * @param car
     * @return
     * @throws Exception
     */
    public Boolean carCheckOut(Parking parking, Car car) throws Exception {

        if (!car.isParked()) {
            return false;
        }
        int parkingSlotID = findParkingSlotForACar(parking, car);

        // We consider that a second equals an hour
        car.setDepartureTime(LocalDateTime.now());

        // Calculate the duration of the parking and the bill
        Duration duration = Duration.between(car.getArrivalTime(), car.getDepartureTime());
        PricingPolicy pricingPolicy = parking.getPricingPolicy();
        BigDecimal bill = pricingPolicy.calculateParkingFee(Math.toIntExact(duration.getSeconds() + 1));

        car.setParked(false);
        car.setParkingSlotID(null);
        car.setParkingFee(bill);

        logger.log(Level.INFO, MessageBuilder.buildCarLeaveMessage(car.getCarID(), car.getCarType().name(),
                parkingSlotID, duration.getSeconds() + 1, car.getParkingFee(), car.getDepartureTime()));

        return true;
    }

    synchronized int findParkingSlotForACar(Parking parking, Car car) throws TollParkingException {
        // Get the parking slot of this car
        Optional<ParkingSlot> parkingSlot = parking.getParkingSlotsMap().get(car.getCarType())
                .stream()
                .filter(p -> p.getParkingSlotID() == car.getParkingSlotID())
                .filter(p -> p.isOccupied() == true)
                .findFirst();

        // Throw a exception if the car is not in the parking
        if (!parkingSlot.isPresent()) {
            throw new TollParkingException("CAR_NOT_FOUND", car.getCarID().toString());
        }

        // Free the parking slot
        parkingSlot.get().setOccupied(false);

        return parkingSlot.get().getParkingSlotID();
    }

}
