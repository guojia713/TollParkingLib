package com.tollparking.api;

import com.tollparking.api.model.Car;
import com.tollparking.api.model.CarTypeEnum;
import com.tollparking.api.model.Parking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TollParkingTest {

    private static TollParking tollParking  = new TollParking();;

    private static Random random = new Random();

    private final int CARS_QUANTITY = 10; // The total quantity of cars

    private final int NB_ENTRIES = 5; // Nb of entries

    @Test
    void carCheckInTest() throws InterruptedException {
        Parking parking = new Parking();

        Car car = new Car();
        assertTrue(tollParking.carCheckIn(parking, car));
    }

    @Test
    void carCheckOutTest() throws Exception {

        Parking parking = new Parking();


        Car car0 = new Car();
        if(tollParking.carCheckIn(parking, car0)) {
            assertTrue(tollParking.carCheckOut(parking, car0));
        }

        Car car1 = new Car(1, getRandomCarType());
        assertFalse(tollParking.carCheckOut(parking, car1));
    }



    @Test
    @DisplayName("EndToEndTest")
    void tollParkingTest() throws InterruptedException {

        //Initialization of the toll parking
        Parking parking = new Parking();

        List<Car> cars = new ArrayList<>();
        IntStream.range(0,CARS_QUANTITY).forEach(index -> cars.add(new Car(index, getRandomCarType())));

       // We say that the nb of entries = nb of threads
        ExecutorService executor = Executors.newFixedThreadPool(NB_ENTRIES);
        List<Callable<Boolean>> callables = new ArrayList<>();

        // Cars arrive in order
       cars.stream()
            .forEach(car->{
                callables.add(carCheckIn(parking, car));
                callables.add(carCheckOut(parking, car));
            });

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get(20, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
        executor.shutdown();

    }

    /**
     * Callable to check in the car
     * @param car
     * @return
     */
    private static Callable<Boolean> carCheckIn(Parking parking, Car car) {
        return () -> {
            TimeUnit.SECONDS.sleep(random.nextInt(10) + 0);
            return tollParking.carCheckIn(parking,car);
        };
    }

    /**
     * Callable to checkout the car
     * @param car
     * @return
     */
    private static Callable<Boolean> carCheckOut(Parking parking, Car car) {
        return () -> {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(10) + 5);
            return tollParking.carCheckOut(parking, car);
        };
    }

    /**
     * Get random type of the car
     * @return CarTypeEnum
     */
    private CarTypeEnum getRandomCarType(){
        final List<CarTypeEnum> values = Collections.unmodifiableList(Arrays.asList(CarTypeEnum.values()));
        return values.get(new Random().nextInt(values.size()));
    }
}