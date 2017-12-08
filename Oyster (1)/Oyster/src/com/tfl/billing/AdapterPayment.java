package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;
import com.tfl.underground.OysterReaderLocator;

public class AdapterPayment implements UniversalPaymentSystem{

	private static AdapterPayment instance = new AdapterPayment();
	
	private PaymentsSystem paymentsSystem = PaymentsSystem.getInstance();
	
	public static AdapterPayment getInstance()
	{
		return instance;
	}
	@Override
	public void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill) 
	{
		paymentsSystem.charge(customer, journeys, totalBill);
	}
	
	

	@Override
	public String stationwithReader(UUID originId)
	{
		return OysterReaderLocator.lookup(originId).name();
	}
	

}
