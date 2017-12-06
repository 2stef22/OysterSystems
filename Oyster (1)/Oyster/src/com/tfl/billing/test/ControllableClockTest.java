package com.tfl.billing.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

import org.junit.Test;

import com.tfl.billing.ControllableClock;

public class ControllableClockTest {
	
	
	public Calendar startClockandCalendar(int hour, int min) {
		ControllableClock clock = new ControllableClock();
		
		Calendar calendar = Calendar.getInstance();
		clock.setCurrentTime(hour,min);
		calendar.setTimeInMillis(clock.timeNow());
		
		return calendar;
	}
	@Test
	public void testHourControllableClock()
	{
		Calendar calendar = startClockandCalendar(7,0);
		assertEquals(7,calendar.get(Calendar.HOUR_OF_DAY));
	}
	
	@Test
	public void testMinuteControllableClock()
	{
		Calendar calendar = startClockandCalendar(0,31);
		assertEquals(31,calendar.get(Calendar.MINUTE));
	}
	
	@Test
	public void testIfclockResets()
	{
		ControllableClock clock = new ControllableClock();
		Calendar calendar = startClockandCalendar(19,50);
		clock.setCurrentTime(19, 50);
		assertEquals(19,calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(50,calendar.get(Calendar.MINUTE));
		clock.resetClock();
		assertEquals(clock.timeNow(),1512259200000L);
	}
	
	
	

}
