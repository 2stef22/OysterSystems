package com.tfl.billing;


public class ControllableClock implements Clock {
	private long now = 1512259200000L;
	
	//03/12/17 00:00:00  - European Calendar
	@Override	
	public long timeNow()
	{
		return now;
	}
	public void resetClock()
	{
		now =  1512259200000L;
	}
	public void setCurrentTime(int hour,int min) 
	{
		now = now + ((hour * 3600000) + (min *60000));
	}


}
