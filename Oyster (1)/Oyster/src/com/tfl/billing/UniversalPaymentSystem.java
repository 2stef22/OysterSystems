package com.tfl.billing;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.tfl.external.Customer;

public interface UniversalPaymentSystem {
	
	public void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill);
	public String stationwithReader(UUID originID);

}
