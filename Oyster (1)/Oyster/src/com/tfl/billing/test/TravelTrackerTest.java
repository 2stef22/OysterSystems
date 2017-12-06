package com.tfl.billing.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
import com.tfl.billing.ControllableClock;
import com.tfl.billing.Database;
import com.tfl.billing.Journey;
import com.tfl.billing.JourneyEnd;
import com.tfl.billing.JourneyEvent;
import com.tfl.billing.JourneyStart;
import com.tfl.billing.TravelTracker;
import com.tfl.billing.UniversalPaymentSystem;
import com.tfl.billing.UnknownOysterCardException;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;
public class TravelTrackerTest  {
	
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	Database mockDB = context.mock(Database.class);
    UniversalPaymentSystem mockPayment = context.mock(UniversalPaymentSystem.class);
    TravelTracker travelTracker = new TravelTracker(mockDB, mockPayment);
    private List<Journey> journeysDone = new ArrayList<Journey>();
    private List<Customer> fakeCustomers = new ArrayList<Customer>() {
    	{
            this.add(new Customer("Freddie Brooks", new OysterCard("baabe53c-d946-11e7-9296-cec278b6b50a")));
    	}
    };

    @Test  (expected = UnknownOysterCardException.class)
   	public void checkIfExceptionIstThrownWhenUnknownCard() {
            
            UUID customerCardId=UUID.fromString("38410000-8cf0-11bd-b23e-10b96e4ef00d");
            UUID stationReaderId=UUID.randomUUID();
            travelTracker.cardScanned(customerCardId, stationReaderId);
    }
            
    @Test
    public void testIftheDatabseCanBeRetrieved()
    {
    	context.checking(new Expectations() {{ 
    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	}});
    	
    	mockDB.getCustomers();
    }
    
    @Test 
    public void testIfTheCustomerIsCharged()
    {

    	context.checking(new Expectations() {{
    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    		oneOf(mockPayment).charge(fakeCustomers.get(0), journeysDone ,roundToNearestPenny(new BigDecimal(0)));
    		
    	}});
    	
    	travelTracker.chargeAccounts();
    	
    	
    }
    
    
    @Test
    public void checkTripCostPeakLong()
    {
    	TravelTracker travelTracker = createJourneyEventsToTestCharges(17,17,00,26);
    	BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(3.80));	
		context.checking(new Expectations() {{
    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	}});
	 
		 
	     travelTracker.chargeAccounts();
	   	 
    }
    
    @Test
    public void checkTripCostPeakShort()
    {
    	TravelTracker travelTracker = createJourneyEventsToTestCharges(07,07,23,30);
    	BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(2.90));		
    		context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    		oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	    	
    	    	}});
    		  
    		     travelTracker.chargeAccounts();
    		   	 
    	    }
    

    @Test
    public void checkTripCostOffPeakLong()
    {
    	    	//TravelTracker travelTracker = createJourneyEventsToTestCharges(14,15,11,12);
    	    	TravelTracker travelTracker = create5JourneyEventsforCap(14,5,1,0,26);
    			BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(2.70));
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    		oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	    	}});
    		   
    			
    			
    		     travelTracker.chargeAccounts();
    		   	 
    	    }

	
    
    @Test
    public void checkTripCostOffPeakShort()
    {
    	TravelTracker travelTracker = createJourneyEventsToTestCharges(21,21,03,10);
    	BigDecimal customerTotal = roundToNearestPenny(new BigDecimal(1.60));	
    		   	
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotal))));
    	    	
    	    	}});
    		  
    			 
    		     travelTracker.chargeAccounts();
    		   	 
    	    }
    
    /*@Test
    public void checkTripCostOffPeakCap()
    {
    	TravelTracker travelTracker =create5JourneyEventsforCap(16,0,5,0,25);
    	BigDecimal customerTotal = roundToNearestPenny(new BigDecimal(9.00));	
    		   	
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotal))));
    	    	
    	    	}});
    		  
    		
    			 
    		     travelTracker.chargeAccounts();
    		   	 
    	    }*/
    @Test
    public void checkTripCostPeakCap()
    {
    	TravelTracker travelTracker =create5JourneyEventsforCap(17,55,5,0,3);
    	BigDecimal customerTotal = roundToNearestPenny(new BigDecimal(9.00));	
    		   	
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotal))));
    	    	
    	    	}});
    		  
    			 
    		     travelTracker.chargeAccounts();
    		   	 
    	    }
    
    public TravelTracker createJourneyEventsToTestCharges(int hour1,int hour2, int min1, int min2) {
		ControllableClock clock = new ControllableClock();
		List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
		
		 OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
		 OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
 		
		 UUID myCard = fakeCustomers.get(0).cardId();
		 
		 clock.setCurrentTime(hour1, min1);
		 JourneyEvent myJourneyEvent1 = new JourneyStart(myCard,paddingtonReader.id(), clock);
		 clock.resetClock();
		 clock.setCurrentTime(hour2, min2);
		 JourneyEvent myJourneyEvent2 = new JourneyEnd(myCard, bakerStreetReader.id(), clock);
		 
		 
		 eventLog.add(myJourneyEvent1);
		 eventLog.add(myJourneyEvent2);
 		 
		
		 TravelTracker travelTrackerPrices = new TravelTracker(mockDB,mockPayment,clock,eventLog);
		return travelTrackerPrices;
	}
    
    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
    	return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    

    public TravelTracker create5JourneyEventsforCap(int hour, int min,int noJourneys, int hourDifference, int minDifference) {
		ControllableClock clock = new ControllableClock();
		List<Journey> journeys = new ArrayList<Journey>();
		List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
		OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
		OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
		clock.resetClock();
		UUID myCard = fakeCustomers.get(0).cardId();
		for (int x = 0; x < noJourneys * 5 ; x=x+5 )
		{
			min = min + x;
			if (min > 59)
			{
				min = min - 60;
				hour = hour + 1;

			}

		
			clock.setCurrentTime(hour, min);
			System.out.println(clock.timeNow());
			JourneyEvent myJourneyEvent1 = new JourneyStart(myCard, paddingtonReader.id(), clock);
			if (min > 59)
			{
				min = min - 60;
				hour = hour + 1;

			}
		
			clock.resetClock();
			clock.setCurrentTime(hour + hourDifference, min+ minDifference);
			System.out.println(clock.timeNow());
			JourneyEvent myJourneyEvent2 = new JourneyEnd(myCard, bakerStreetReader.id(), clock);
			eventLog.add(myJourneyEvent1);
			eventLog.add(myJourneyEvent2);

		}
		TravelTracker travelTrackerJourneys = new TravelTracker(mockDB,mockPayment,clock,eventLog);
		return travelTrackerJourneys;
	}
}
    
    


	
	
	
	
	
	
	
	
	  

	
	