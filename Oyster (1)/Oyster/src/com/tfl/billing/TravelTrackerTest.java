package com.tfl.billing;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;


public class TravelTrackerTest  {
	UUID customerCardId , stationReaderId;
	TravelTracker travelTracker = new TravelTracker();
	
	
	@Test  (expected = UnknownOysterCardException.class)
	public void checkIfExceptionIstThrownWhenUnknownCard()
    {
        
        customerCardId=UUID.fromString("38410000-8cf0-11bd-b23e-10b96e4ef00d");
        stationReaderId=UUID.randomUUID();
        
        
        travelTracker.cardScanned(customerCardId, stationReaderId);
    }
	
	/*@Test
	public void checkEventLogArrayList()
	{
		OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
		OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
		//List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
		 
		 travelTracker.connect(paddingtonReader, bakerStreetReader);
		 paddingtonReader.touch(myCard);
		
		 bakerStreetReader.touch(myCard);
		 travelTracker.chargeAccounts();
		 assertEquals(travelTracker.eventLog.size(), 1);
	}
	
	@Test 
	public void checkRoundUpMethod()
	{
		  double c=47.48000;
	      BigDecimal b = new BigDecimal(c);
		
		assertEquals(travelTracker.roundToNearestPenny(b), new BigDecimal("47.48"));
	}*/
	
	

	
	
	
	
	
	
	
	
	  

	
	
}
