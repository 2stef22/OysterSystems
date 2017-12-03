package com.tfl.billing;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;

import org.jmock.integration.junit4.JUnitRuleMockery;

public class TravelTrackerTest  {
	UUID customerCardId , stationReaderId;
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	Database database = context.mock(Database.class);
	
	UniversalPaymentSystem payment = context.mock(UniversalPaymentSystem.class);
	
	ControllableClock clock = new ControllableClock();
	
    TravelTracker travelTracker = new TravelTracker(database,payment, clock);
   
    
    
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
    
    private static void minutesPass(int n) throws InterruptedException {
		 Thread.sleep(n *  1000);
		 }

	@Test
    public void testifnotpeak()
    {
    
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
	
    
	/*
	@Test  (expected = UnknownOysterCardException.class)
	public void checkIfExceptionIstThrownWhenUnknownCard()
    {
        
        customerCardId=UUID.fromString("38410000-8cf0-11bd-b23e-10b96e4ef00d");
        stationReaderId=UUID.randomUUID();
        
        
        travelTracker.cardScanned(customerCardId, stationReaderId);
    }*/
	
	
	
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
