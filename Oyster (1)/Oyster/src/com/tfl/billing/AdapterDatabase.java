package com.tfl.billing;

import java.util.List;
import java.util.UUID;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

public class AdapterDatabase implements Database{

	public static AdapterDatabase instance = new AdapterDatabase();
	
	private CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
	
	public static AdapterDatabase getInstance() {
		return instance;
	}
	
	@Override
	public List<Customer> getCustomers() {
		return customerDatabase.getCustomers();
	}

	@Override
	public boolean isRegisteredId(UUID cardId) {
		return customerDatabase.isRegisteredId(cardId);
		
	}
	
}
