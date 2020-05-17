package com.tollparking.api.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayByHoursWithFixedAmountTest {

    @Test
    void calculateParkingFee() {
        PricingPolicy pricingPolicy = new PayByHoursWithFixedAmount(BigDecimal.valueOf(5), BigDecimal.valueOf(0.5));
        assertEquals(BigDecimal.valueOf(9.0), pricingPolicy.calculateParkingFee(8), "The calculation of parking fee is incorrect");
    }
}