package com.tfl.billing;

import java.util.UUID;

public abstract class JourneyEvent {

    private final UUID cardId;
    private final UUID readerId;
    private final long time;
    //private Clock clock;
    public JourneyEvent(UUID cardId, UUID readerId) {
        this.cardId = cardId;
        this.readerId = readerId;
        this.time =  System.currentTimeMillis();//clock.timeNow(); //
    }
    
    public JourneyEvent(UUID cardId, UUID readerId , Clock clock) {
        this.cardId = cardId;
        this.readerId = readerId;
        this.time = clock.timeNow(); 
    }

    public UUID cardId() {
        return cardId;
    }

    public UUID readerId() {
        return readerId;
    }

    public long time() {
        return time;
    }
}
