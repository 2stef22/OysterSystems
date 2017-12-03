package com.tfl.billing;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class JourneyTest {
	
	UUID customerCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    UUID stationReaderIdStart = UUID.fromString("38403333-8cf0-11bd-b23e-10b96e4ef00d");
    UUID stationReaderIdEnd = UUID.fromString("38403334-8cf0-11bd-b23e-10b96e4ef00d");

	public Journey createClockandJourney() {
		ControllableClock clock = new ControllableClock();
		clock.setCurrentTime(19,20);
		JourneyEvent myJourneyEvent1 = new JourneyStart(customerCardId, stationReaderIdStart, clock);
	    clock.setCurrentTime(0,20);
	    JourneyEvent myJourneyEvent2 = new JourneyEnd(customerCardId, stationReaderIdEnd, clock);
	    Journey myJourney = new Journey(myJourneyEvent1, myJourneyEvent2);
		return myJourney;
	}
	
	@Test
	public void testOriginId() {
		Journey myJourney = createClockandJourney();
	    assertEquals(stationReaderIdStart,myJourney.originId());
	   
	}
	
	@Test
	public void testReaderEnd()
	{
		Journey myJourney = createClockandJourney();
		 assertEquals(stationReaderIdEnd,myJourney.destinationId());
	}

	@Test
	public void  checkStartDuration()
	{
		
		Journey myJourney = createClockandJourney();
	    assertEquals(myJourney.startTime().toString(),"Sun Dec 03 19:20:00 GMT 2017");
	    
	}  
	    
	@Test
	public void checkEndDuration()
	{
		Journey myJourney = createClockandJourney();
		assertEquals(myJourney.endTime().toString(),"Sun Dec 03 19:40:00 GMT 2017");
	}
	    
	public void checkDurationInSeconds()
	{
		Journey myJourney = createClockandJourney();
		assertEquals(myJourney.durationSeconds(), 20*60);   
	}
	public void checkDurationInHours()
	{
		Journey myJourney = createClockandJourney();
		assertEquals(myJourney.durationMinutes(), "20:0");
	}
	public void checkFormattedStartTime()
	{
		Journey myJourney = createClockandJourney();
		assertEquals(myJourney.formattedStartTime(),"03/12/17 19:20" );
	    
	}
	public void checkFormattedEndTime()
	{
		Journey myJourney = createClockandJourney();
	    assertEquals(myJourney.formattedEndTime(), "03/12/17 19:40");
	}
	
	
	
	
	
	

	

}
