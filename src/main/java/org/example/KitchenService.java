package org.example;

public class KitchenService extends RoomService {

    String description;

    public KitchenService(String description) {
        super(true); // kitchen orders are always chargable
        this.description = description;
    }

    public void printService() {
        System.out.println("Kitchen Service Order: " + description);
        System.out.println("Chargable: " + isChargable);
        System.out.println("Requested at: " + requestTime);
    }
}