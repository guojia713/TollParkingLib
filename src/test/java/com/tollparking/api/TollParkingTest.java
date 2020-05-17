package com.tollparking.api;

import com.tollparking.api.model.Car;
import com.tollparking.api.model.CarTypeEnum;
import com.tollparking.api.model.Parking;
import com.tollparking.api.model.PayByHoursWithFixedAmount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class TollParkingTest {

    private static TollParking tollParking;

    private static Random random = new Random();

    private final int CARS_QUANTITY = 10; // The total quantity of cars

    private final int NB_ENTRIES = 5; // Nb of entries


    @BeforeAll
    static void init() {
        tollParking = new TollParking();
    }

    @Test
    @DisplayName("EndToEndTest")
    void tollParkingTest() throws InterruptedException {

        //Initialization of the toll parking
        Parking parking = new Parking(2,2,2, new PayByHoursWithFixedAmount(BigDecimal.valueOf(10),BigDecimal.valueOf(0.5)));

        List<Car> cars = new ArrayList<>();
        IntStream.range(0,CARS_QUANTITY).forEach(index -> cars.add(new Car(index, getRandomCarType())));

       // We say that the nb of entries = nb of threads
        ExecutorService executor = Executors.newFixedThreadPool(NB_ENTRIES);
        List<Callable<Car>> callables = new ArrayList<>();

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
                        return future.get(100, TimeUnit.SECONDS);
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
    private static Callable<Car> carCheckIn(Parking parking, Car car) {
        return () -> {
            TimeUnit.SECONDS.sleep(random.nextInt(10) + 0);
            tollParking.carCheckIn(parking,car);
            return car;
        };
    }

    /**
     * Callable to checkout the car
     * @param car
     * @return
     */
    private static Callable<Car> carCheckOut(Parking parking, Car car) {
        return () -> {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(10) + 5);
            tollParking.carCheckOut(parking, car);
            return car;
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