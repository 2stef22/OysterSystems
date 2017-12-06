package com.tfl.billing;
import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
public class Example {

		 public static void main(String[] args) throws Exception {
		
		
	   
			 OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		 OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
		 OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
		 OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);
		 OysterCardReader eustonReader = OysterReaderLocator.atStation(Station.EUSTON);
		 

		 
		 ControllableClock clock = new ControllableClock();
		 //clock.setCurrentTime(15);
		 //System.out.println(clock.timeNow());
		 //System.out.println(System.currentTimeMillis());
		 //TravelTracker travelTracker = new TravelTracker(clock);
		 TravelTracker travelTracker =new TravelTracker();
		 
		 travelTracker.connect(paddingtonReader, bakerStreetReader, kingsCrossReader, eustonReader);
		 
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 
		/* paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		 paddingtonReader.touch(myCard);
		 minutesPass(5);
		 bakerStreetReader.touch(myCard);
		
		 */
		 //paddingtonReader.touch(myCard1);
		 //minutesPass(1);
		 //bakerStreetReader.touch(myCard1);
	
		 
		

		 
		 travelTracker.chargeAccounts();
		 }
		 private static void minutesPass(int n) throws InterruptedException {
		 Thread.sleep(n *  1000);
		 }
		}


