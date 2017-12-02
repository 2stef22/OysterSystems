package com.tfl.billing;

public class ControllableClock implements Clock {
	
	public int currenttime;
	public ControllableClock(int time) {currenttime = time;
	}
	
	@Override
	public int hourNow() {
		
		return currenttime;
	}

}
