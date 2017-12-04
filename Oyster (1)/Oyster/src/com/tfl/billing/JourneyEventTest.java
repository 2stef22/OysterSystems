package com.tfl.billing;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class JourneyEventTest {
	UUID customerCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
	UUID stationReaderIdStart = UUID.randomUUID();
	UUID stationReaderIdEnd = UUID.randomUUID();

	public JourneyEvent createJourneyStart() {
		JourneyEvent myJourneyEventStart = new JourneyStart(customerCardId, stationReaderIdStart);
		return myJourneyEventStart;

	}

	public JourneyEvent createJourneyEnd() {
		JourneyEvent myJourneyEventEnd = new JourneyEnd(customerCardId, stationReaderIdEnd);
		return myJourneyEventEnd;
	}

	@Test
	public void TestCardIdStart() {
		JourneyEvent myJourneyEventStart = createJourneyStart();
		assertEquals(myJourneyEventStart.cardId(), customerCardId);
	}

	@Test
	public void TestReaderIdStart() {
		JourneyEvent myJourneyEventStart = createJourneyStart();
		assertEquals(myJourneyEventStart.readerId(), stationReaderIdStart);

	}

	@Test
	public void TestCardIdEnd() {
		JourneyEvent myJourneyEventEnd = createJourneyEnd();
		assertEquals(myJourneyEventEnd.cardId(), customerCardId);
	}

	@Test
	public void TestReaderIdEnd() {
		JourneyEvent myJourneyEventEnd = createJourneyEnd();
		assertEquals(myJourneyEventEnd.readerId(), stationReaderIdEnd);


	}

/*
    @Test
    public void JourneyEventIsCreated() {

        stationReaderId = UUID.randomUUID();
        UUID aCardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

        JourneyEvent myJourneyEvent = new JourneyStart(aCardId, stationReaderId);


        assertEquals(myJourneyEvent.cardId(), aCardId);
        assertEquals(myJourneyEvent.readerId(), stationReaderId);

    }
    */
}

