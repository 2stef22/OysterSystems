package com.tfl.billing;

public class SystemClock implements Clock {

	@Override
	public long timeNow() {
		
		
		return System.currentTimeMillis();
	}

}
