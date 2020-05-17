package com.tollparking.api.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayByHoursTest {

    @Test
    void calculateParkingFee() {
        PricingPolicy pricingPolicy = new PayByHours(BigDecimal.valueOf(0.5));
        assertEquals(BigDecimal.valueOf(4.0), pricingPolicy.calculateParkingFee(8), "The calculation of parking fee is incorrect");
    }
}