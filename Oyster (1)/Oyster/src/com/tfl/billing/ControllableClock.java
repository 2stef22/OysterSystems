package com.tfl.billing;

import java.time.LocalTime;

public class ControllableClock implements Clock {
	private long now = 1512259200000L;
	
	//02/12/2017 13:00:00
	@Override
	
	public long timeNow() {
		return now;
	}
	
	public void setCurrentTime(int hour,int min)  {
		now = now + ((hour * 3600000) + (min *60000));
	}


}
