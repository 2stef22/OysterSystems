package com.tfl.billing;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

import org.junit.Test;

public class TimeTests {
	
	
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
	
	
	
	

}
