package com.tfl.billing;

import com.oyster.*;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;

public class TravelTracker implements ScanListener{
	
	static final BigDecimal OFF_PEAK_JOURNEY_PRICE_SHORT = new BigDecimal(1.60);
    static final BigDecimal PEAK_JOURNEY_PRICE_SHORT = new BigDecimal(2.90);
    static final BigDecimal OFF_PEAK_JOURNEY_PRICE_LONG =new BigDecimal(2.70);
    static final BigDecimal PEAK_JOURNEY_PRICE_LONG = new BigDecimal(3.80);
   
    private List<JourneyEvent> eventLog ; //= new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();
    private Database customerDatabase ; 
    private UniversalPaymentSystem paymentSystem;
    private Clock clock;
   
    /*
     * package com.tfl.billing;

import com.oyster.OysterCard;

public  interface CardReader {
	public void touch(OysterCard card);
}

     */
    public TravelTracker()
    {
    	this.customerDatabase= AdapterDatabase.getInstance();
    	this.paymentSystem = AdapterPayment.getInstance();
    	//this.clock = new SystemClock();
    	this.eventLog = new ArrayList<JourneyEvent>();
    	
    }
   
    public TravelTracker(Database customerDatabase,UniversalPaymentSystem paymentSystem)
    {
    	this.customerDatabase = customerDatabase;
    	this.paymentSystem = paymentSystem;
    	this.eventLog = new ArrayList<JourneyEvent>();
    }
    
    public TravelTracker(Database customerDatabase,UniversalPaymentSystem paymentSystem, Clock clock,List<JourneyEvent> eventLog)
    {
    	this.customerDatabase = customerDatabase;
    	this.paymentSystem = paymentSystem;
    	this.clock = clock;
    	this.eventLog = eventLog;
    }
    
    
    public void chargeAccounts() {
        
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {
        //List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        /*for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
            	
            	customerJourneyEvents.add(journeyEvent);
            }*/
        List<JourneyEvent> customerJourneyEvents = checkIfjourneyCardEqualsCustomerCard(customer);
         //   customerJourneyEvents = checkIfjourneCardEqualsCustomerCard(customer);
    
        
        List<Journey> journeys = new ArrayList<Journey>();
        //List<Journey>
        journeys = createJourneys(customerJourneyEvents, journeys);
        
        //JourneyEvent start = null;
        /*
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }*/
        
        
      //  boolean setPeakCap = false;
      /* //BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
        	BigDecimal journeyPrice ; 
        	boolean isLong = ((journey.durationSeconds()/60) >= 25);
        	boolean isPeak = peak(journey);
  
        	if (isPeak)
        	{
        		setPeakCap = true;
        		if (isLong) {
                    journeyPrice = PEAK_JOURNEY_PRICE_LONG;
                    
                }
        		else journeyPrice = PEAK_JOURNEY_PRICE_SHORT;
        	}
        	else {
        		if (isLong) {
                    journeyPrice = OFF_PEAK_JOURNEY_PRICE_LONG;
                    
                }
        		else 
        		 journeyPrice = OFF_PEAK_JOURNEY_PRICE_SHORT;
        	}
           
           
            
            customerTotal = customerTotal.add(journeyPrice);
        }*/
        BigDecimal customerTotal = calculateCustomerTotal(journeys);
        
      /*  if (setPeakCap && (customerTotal.compareTo(new BigDecimal("9")) == 1))
        {
        	customerTotal = new BigDecimal("9");
        }
        else if (!setPeakCap && (customerTotal.compareTo(new BigDecimal("7")) == 1))
        {
        	customerTotal = new BigDecimal("7");
        }
        	
        */
       paymentSystem.charge(customer, journeys, roundToNearestPenny(customerTotal));
      // AdapterPayment.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
        //PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
    }


	
    private BigDecimal calculateCustomerTotal(List<Journey> journeys) {
    	 boolean setPeakCap = false;
    	BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
        	BigDecimal journeyPrice ; 
        	boolean isLong = ((journey.durationSeconds()/60) >= 25);
        	boolean isPeak = peak(journey);
  
        	if (isPeak)
        	{
        		setPeakCap = true;
        		if (isLong) {
                    journeyPrice = PEAK_JOURNEY_PRICE_LONG;
                    
                }
        		else journeyPrice = PEAK_JOURNEY_PRICE_SHORT;
        	}
        	else {
        		if (isLong) {
                    journeyPrice = OFF_PEAK_JOURNEY_PRICE_LONG;
                    
                }
        		else 
        		 journeyPrice = OFF_PEAK_JOURNEY_PRICE_SHORT;
        	}            
            customerTotal = customerTotal.add(journeyPrice);
        }
        
        return checkCap(customerTotal,setPeakCap);
	}

	private BigDecimal checkCap(BigDecimal customerTotal, boolean setPeakCap) {
		  if (setPeakCap && (customerTotal.compareTo(new BigDecimal("9")) == 1))
	        {
	        	customerTotal = new BigDecimal("9");
	        }
	        else if (!setPeakCap && (customerTotal.compareTo(new BigDecimal("7")) == 1))
	        {
	        	customerTotal = new BigDecimal("7");
	        }
		  return customerTotal;
	        
	
	}

	private List<Journey> createJourneys(List<JourneyEvent> customerJourneyEvents , List<Journey> journeys) {
    	JourneyEvent start = null;
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
		return journeys;
	}

	private ArrayList<JourneyEvent> checkIfjourneyCardEqualsCustomerCard(Customer customer) {
    	ArrayList<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
    
    	for (JourneyEvent journeyEvent : eventLog) {
    	
             if (journeyEvent.cardId().equals(customer.cardId())) {
             	
             	customerJourneyEvents.add(journeyEvent);
             	
             }
    	}
    	
		return customerJourneyEvents;
    }
	private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
    	return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
   

    private boolean peak(Journey journey) {
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    private boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
        } else {
            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
            
            	currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
    }

}

/*	boolean checkDuration(Journey journey)
{  
	int startMinutes, endMinutes , startHour , endHour , duration;
	 Calendar calendar = Calendar.getInstance();
   
    Date theDate = journey.startTime();   
    calendar.setTime(theDate);
    startHour = calendar.get(Calendar.HOUR_OF_DAY);
   	startMinutes = calendar.get(Calendar.MINUTE);
   	System.out.println(startHour);
   	System.out.println(startMinutes);
   	theDate = journey.endTime();   
    calendar.setTime(theDate);
    endHour = calendar.get(Calendar.HOUR_OF_DAY);
   	endMinutes = calendar.get(Calendar.MINUTE);
   	System.out.println(endHour);
   	System.out.println(endMinutes);
   	
   	if (endMinutes > startMinutes) {
   		duration = endMinutes - startMinutes + (endHour - startHour)*60;
   	}
   	else {
   		duration = endMinutes + 60 - startMinutes + (endHour - startHour - 1)*60;
   	}
   	System.out.println(duration);
   	if (duration> 25) 
	return true;
   		else 
   	return false;
   		
   	}
   	*/
	
/*
if (isLong)
{
	if (peak(journey)) {
        journeyPrice = PEAK_JOURNEY_PRICE_LONG;
        setPeakCap = true;
    }
	else journeyPrice = OFF_PEAK_JOURNEY_PRICE_LONG;
}
else {
	if (peak(journey)) {
        journeyPrice = PEAK_JOURNEY_PRICE_SHORT;
        setPeakCap = true;
    }
	else 
	 journeyPrice = OFF_PEAK_JOURNEY_PRICE_SHORT;
}*/
