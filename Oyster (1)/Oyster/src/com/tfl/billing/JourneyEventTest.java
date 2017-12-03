package com.tfl.billing;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class JourneyEventTest {





	 UUID customerCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	    UUID stationReaderIdStart = UUID.randomUUID();
	    UUID stationReaderIdEnd = UUID.randomUUID();

	    JourneyEvent myJourneyEvent1 = new JourneyStart(customerCardId, stationReaderIdStart);
	    JourneyEvent myJourneyEvent2 = new JourneyEnd(customerCardId, stationReaderIdEnd);
	    Journey myJourney = new Journey(myJourneyEvent1, myJourneyEvent2);

	   @Test
	    public void TestOriginId() {
	        assertEquals(myJourney.originId(), stationReaderIdStart);
	       assertEquals(myJourney.startcustomerId(), customerCardId);
	    }

	    @Test
	    public void TestDestinationId() {
	        assertEquals(myJourney.destinationId(), stationReaderIdEnd);
	        assertEquals(myJourney.endcustomerId(), customerCardId);
	    }



	    @Test
	    public void JourneyEventIsCreated() {
	        UUID customerCardId, stationReaderId;
	        //OysterCard myCardTest = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	        stationReaderId = UUID.randomUUID();

	        UUID aCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

	        JourneyEvent myJourneyEvent = new JourneyStart(aCardId, stationReaderId);


	        assertEquals(myJourneyEvent.cardId(), aCardId);
	        assertEquals(myJourneyEvent.readerId(), stationReaderId);

	    }




	   @Test

	    public void JourneyIsCompleted()
	    {
	        UUID customerCardId , stationReaderIdStart,stationReaderIdEnd;
	        //OysterCard myCardTest = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	        stationReaderIdStart =UUID.randomUUID();
	        stationReaderIdEnd =UUID.randomUUID();

	        UUID aCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

	        JourneyEvent myJourneyEvent1 = new JourneyStart(aCardId, stationReaderIdStart);
	        JourneyEvent myJourneyEvent2 = new JourneyEnd(aCardId, stationReaderIdEnd);

	        assertEquals(myJourneyEvent1.cardId(), aCardId);
	        assertEquals(myJourneyEvent1.readerId(), stationReaderIdStart);
	        assertEquals(myJourneyEvent2.cardId(), aCardId);
	        assertEquals(myJourneyEvent2.readerId(), stationReaderIdEnd);


	    }
	   
	   
}