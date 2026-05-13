package org.example;

import java.util.Date;

public class RoomService {

    boolean isChargable;

    Date requestTime;

    public RoomService(boolean isChargable) {
        this.isChargable = isChargable;
        this.requestTime = new Date(); // current time
    }

    public void printService() {
        System.out.println("Chargable: " + isChargable);
        System.out.println("Requested at: " + requestTime);
    }
}