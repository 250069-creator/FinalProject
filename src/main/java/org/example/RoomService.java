package org.example;

import java.util.Date;

// Represents a room service request made by a guest
public class RoomService {

    // Whether this service costs extra money
    boolean isChargable;

    // When the service was requested
    Date requestTime;

    // Constructor
    public RoomService(boolean isChargable) {
        this.isChargable = isChargable;
        this.requestTime = new Date(); // current time
    }

    // Print service details
    public void printService() {
        System.out.println("Chargable: " + isChargable);
        System.out.println("Requested at: " + requestTime);
    }
}