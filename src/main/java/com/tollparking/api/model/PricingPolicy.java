package com.tollparking.api.model;

import java.math.BigDecimal;

/**
 * Interface for the pricing policy
 * @author jiaguo
 */
public interface PricingPolicy {

    BigDecimal calculateParkingFee(int hours);
}
