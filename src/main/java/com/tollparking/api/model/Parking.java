package com.tollparking.api.model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Class of Parking
 * @author jiaguo
 */
public class Parking {

    private Map<CarTypeEnum, List<ParkingSlot>> parkingSlotsMap; // The list of all of the parking slots in the toll parking

    private PricingPolicy pricingPolicy; // The pricing policies

    /**
     * Constructor
     * Initialization of the toll parking by default
     *
     * Set up 10 slots for each kinds of parking
     * Set up the pricing policy = nb hours * hour price
     */
    public Parking() {
        initParkingSlots(2, 2, 2);
        this.pricingPolicy = new PayByHours();
    }

    /**
     * Constructor
     * Initialization of the toll parking with the given parameters
     *
     * @param nbSedanCar         Number of the slots for sedan cars
     * @param nbElectricCar_20kw Number of the slots for electric cars with 20kw
     * @param nbElectricCar_50kw Number of the slots for electric cars with 50kw
     * @param pricingPolicy      The pricing policy
     */
    public Parking(int nbSedanCar, int nbElectricCar_20kw, int nbElectricCar_50kw, PricingPolicy pricingPolicy) {
        initParkingSlots(nbSedanCar, nbElectricCar_20kw, nbElectricCar_50kw);
        this.pricingPolicy = pricingPolicy;
    }

    /**
     * Initialization the parking slots
     *
     * @param nbSedanCar
     * @param nbElectricCar_20kw
     * @param nbElectricCar_50kw
     */
    private void initParkingSlots(int nbSedanCar, int nbElectricCar_20kw, int nbElectricCar_50kw) {

        initParkingSlotsForEachType(nbSedanCar, CarTypeEnum.SEDAN_CAR);
        initParkingSlotsForEachType(nbElectricCar_20kw, CarTypeEnum.ELECTRIC_CAR_20KW);
        initParkingSlotsForEachType(nbElectricCar_50kw, CarTypeEnum.ELECTRIC_CAR_50KW);
    }

    /**
     * Create parking slots for each type
     *
     * @param nbParkingSlots
     * @param authorizedCarType
     */
    private void initParkingSlotsForEachType(int nbParkingSlots, CarTypeEnum authorizedCarType) {
        // Create a synchronized list for the parking slots
        List<ParkingSlot> parkingSlots = Collections.synchronizedList(new ArrayList<>());

        IntStream.range(0, nbParkingSlots).forEach(p -> {
            ParkingSlot parkingSlot = new ParkingSlot();
            parkingSlot.setOccupied(false);
            parkingSlot.setAuthoredCarType(authorizedCarType);
            parkingSlots.add(parkingSlot);
        });

        this.getParkingSlotsMap().put(authorizedCarType, parkingSlots);
    }

    /**
     * Get the map of all the parking slot
     *
     * @return Map<CarTypeEnum, List <ParkingSlot>>
     */
    public Map<CarTypeEnum, List<ParkingSlot>> getParkingSlotsMap() {
        if (parkingSlotsMap == null || parkingSlotsMap.isEmpty()) {
            return parkingSlotsMap = new HashMap<>();
        }
        return parkingSlotsMap;
    }

    /**
     * Get Pricing policy
     *
     * @return
     */
    public PricingPolicy getPricingPolicy() {
        return pricingPolicy;
    }

}