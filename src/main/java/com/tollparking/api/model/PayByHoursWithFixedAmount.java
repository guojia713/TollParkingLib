package com.tollparking.api.model;

import java.math.BigDecimal;

/**
 * Class PayByHoursWithFixedAmount
 * @author jiaguo
 */
public class PayByHoursWithFixedAmount implements PricingPolicy {

    private BigDecimal fixedAmount; // The fixed amount

    private BigDecimal hourPrice; // The price per hour

    /**
     * Constructor
     * @param fixedAmount The fixed amount
     * @param hourPrice The price per hour
     */
    public PayByHoursWithFixedAmount(BigDecimal fixedAmount, BigDecimal hourPrice){
        this.fixedAmount = fixedAmount;
        this.hourPrice = hourPrice;
    }

    /**
     * Calculate the parking fee
     * parking fee = fixed amount + hour price * nb hours
     * @param hours the number of parking hours
     * @return BigDecimal parking fee
     */
    @Override
    public BigDecimal calculateParkingFee(int hours){
        return this.fixedAmount
                .add(this.hourPrice.multiply(new BigDecimal(hours)));
    }

}
