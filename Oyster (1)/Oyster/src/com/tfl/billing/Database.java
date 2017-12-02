package com.tfl.billing;
import java.util.List;
import java.util.UUID;

import com.tfl.external.Customer;

public interface Database {
	//static Database getInstance() {
		
	//}
	
	List<Customer> getCustomers();
	
	boolean isRegisteredId(UUID cardId);
}
