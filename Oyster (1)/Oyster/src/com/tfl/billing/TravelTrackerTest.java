package com.tfl.billing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
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
	TravelTracker travelTracker = new TravelTracker(mockDB);
    UniversalPaymentSystem mockPayment = context.mock(UniversalPaymentSystem.class);
    TravelTracker travelTracker2 = new TravelTracker(mockDB, mockPayment);
    private List<Journey> journeysDone = new ArrayList<Journey>();
    private BigDecimal cost = new BigDecimal(0);
    ControllableClock clock = new ControllableClock();
    private List<Customer> fakeCustomers = new ArrayList<Customer>() {
    	{
            this.add(new Customer("Freddie Brooks", new OysterCard("baabe53c-d946-11e7-9296-cec278b6b50a")));
    	}
        };

    
    @Test
    public void testIfwecanGettheDb()
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
    		oneOf(mockPayment).charge(fakeCustomers.get(0), journeysDone ,roundToNearestPenny(cost));
    		
    	}});
    	
    	travelTracker2.chargeAccounts();
    	
    	
    }
    
    
    @Test
    public void checkTripCostPeakLong()
    {
    	TravelTracker travelTracker3 = createJourneyEventsToTestCharges(17,17,00,26);
    	BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(3.80));	
		context.checking(new Expectations() {{
    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	}});
	 
		 
	     travelTracker3.chargeAccounts();
	   	 
    }
    
    @Test
    public void checkTripCostPeakShort()
    {
    	TravelTracker travelTracker3 = createJourneyEventsToTestCharges(07,07,23,30);
    	BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(2.90));		
    		context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    		oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	    	
    	    	}});
    		  
    		     travelTracker3.chargeAccounts();
    		   	 
    	    }
    

    @Test
    public void checkTripCostOffPeakLong()
    {
    	    	TravelTracker travelTracker3 = createJourneyEventsToTestCharges(14,15,11,12);
    			BigDecimal customerTotalpeakl = roundToNearestPenny(new BigDecimal(2.70));
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    		oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotalpeakl))));
    	    	}});
    		   
    			
    			
    		     travelTracker3.chargeAccounts();
    		   	 
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
 		 
 		
		 TravelTracker travelTracker3 = new TravelTracker(mockDB,mockPayment,clock,eventLog);
		return travelTracker3;
	}
    
    
    @Test
    public void checkTripCostOffPeakShort()
    {
    	TravelTracker travelTracker3 = createJourneyEventsToTestCharges(21,21,03,10);
    	BigDecimal customerTotal = roundToNearestPenny(new BigDecimal(1.60));	
    		   	
    			context.checking(new Expectations() {{
    	    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    	    	 oneOf(mockPayment).charge(with(equal(fakeCustomers.get(0))),  with(aNonNull(ArrayList.class)) ,with(equal(roundToNearestPenny(customerTotal))));
    	    	
    	    	}});
    		  
    			 
    		     travelTracker3.chargeAccounts();
    		   	 
    	    }
    

    

    
    @Test  (expected = UnknownOysterCardException.class)
	public void checkIfExceptionIstThrownWhenUnknownCard()
    {
        
        UUID customerCardId=UUID.fromString("38410000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID stationReaderId=UUID.randomUUID();
        
        
        travelTracker.cardScanned(customerCardId, stationReaderId);
    }
    
    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
    	return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}

	        
    	 
    	 
    	 
    
    
    
    /*
    @Test
    public void checkPrices()
    {
    	ControllableClock clock = new ControllableClock();
		 OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		 OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
		 OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
		 travelTracker.connect(paddingtonReader, bakerStreetReader);
		 clock.setCurrentTime(19, 51);
		 paddingtonReader.touch(myCard);
		 clock.setCurrentTime(0, 5);
		 bakerStreetReader.touch(myCard);
		 
		 context.checking(new Expectations() { { 
	        	exactly(1).of(mockablePayment).charge();
	        	
	        } });
		 
		 travelTracker.chargeAccounts();
		 
		  
    }*/
    /*
  
   @Test
    public void testIfConnectIsCalled()
    {
    	OysterCardReader one = new OysterCardReader();
    	OysterCardReader two = new OysterCardReader();
    	OysterCard three = new OysterCard();
    	ScanListener mockScan = context.mock(ScanListener.class);
    	
        UUID customerCardId=UUID.randomUUID();
        UUID stationReaderId=UUID.randomUUID();
        
    	context.checking(new Expectations() {{
    		oneOf(mockDB).getCustomers(); will(returnValue(fakeCustomers));
    		oneOf(mockScan).cardScanned(customerCardId, stationReaderId);
    		    	
    	}});
    	
    	one.touch(three);
    	//travelTracker.chargeAccounts();
    

    	

    }
    @Test
    public void testIfaSingleTravellerIsTracked()
    {
    	Database mockableDb = context.mock(Database.class);
    	OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    	UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
       	UUID stationReaderId =UUID.randomUUID();
  
       	
       	travelTracker = new TravelTracker(mockableDb);
        
        context.checking(new Expectations() { { 
        	exactly(1).of(mockableDb).isRegisteredId(cardId);
        	
        } });
        
       
        travelTracker.cardScanned(cardId, stationReaderId);
        //assertTrue(currentlyTravelling.contains(cardId));
    } */
    
	
	

    
    
    
    
    /*
    
    @Test
    public void JourneyEventIsCreated() throws InterruptedException {
        UUID customerCardId, stationReaderId;
        //OysterCard myCardTest = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        stationReaderId = UUID.randomUUID();

        UUID aCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

        JourneyEvent myJourneyEvent = new JourneyStart(aCardId, stationReaderId,clock);
        //clock.setCurrentTime(hour);
        clock.setCurrentTime(0, 25);
        JourneyEvent myJourneyEvent1 = new JourneyEnd(aCardId, stationReaderId,clock);
        
        Journey myJ = new Journey(myJourneyEvent,myJourneyEvent1);
        
        System.out.println(myJ.startTime());
        
        System.out.println(myJ.endTime());
        System.out.println(myJ.durationSeconds()/60);
        assertEquals(myJourneyEvent.readerId(), stationReaderId);

    }
    

    	
    @Test
	public void testIfPeak()
	{
		
		ControllableClock clock = new ControllableClock();
		clock.setCurrentTime(17,56);
		TravelTracker travelTracker = new TravelTracker(clock);
		
		UUID customerCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	    UUID stationReaderId = UUID.randomUUID();
	    
		JourneyEvent myJourneyEvent1 = new JourneyStart(customerCardId, stationReaderId, clock);
		clock.setCurrentTime(17, 59);
	    JourneyEvent myJourneyEvent2 = new JourneyEnd(customerCardId, stationReaderId,clock);
	    Journey myJourney = new Journey(myJourneyEvent1, myJourneyEvent2);
	    
	   
	    assertEquals(travelTracker,true);
	    
	}
    
    @Test
    public void TheTest()
    {
    	 context.checking(new Expectations() {{
			 exactly(1).of(database).getCustomers();
		 }});
		 
    	OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    	OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
	    OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
	    travelTracker.connect(paddingtonReader, bakerStreetReader);
		 
		paddingtonReader.touch(myCard);
		 travelTracker.chargeAccounts();
		 
		
    }
    */
    
    /*
    @Test
    public void theSecondTest()
    {
    	OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    	UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    	
    	 context.checking(new Expectations() {{
			// exactly(1).of(database).getCustomers();
			 exactly(1).of(database).isRegisteredId(cardId); will(returnValue(true));
		 }});
    	
		 
    	
    	OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
	    OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
	    travelTracker.connect(paddingtonReader, bakerStreetReader);
		
	    //OysterCardReader randomStation
		 paddingtonReader.touch(myCard);
		 
		 
		 travelTracker.chargeAccounts();
		 
    }
  
		 private UUID fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
		private static void minutesPass(int n) throws InterruptedException {
			 Thread.sleep(n *  1000);
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
	
	

	
	
	
	
	
	
	
	
	  

	
	