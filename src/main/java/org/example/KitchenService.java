package org.example;

// Kitchen service is a type of room service
// for ordering food to the room
// Extends RoomService
public class KitchenService extends RoomService {

    // Description of the food order
    String description;

    // Constructor
    public KitchenService(String description) {
        super(true); // kitchen orders are always chargable
        this.description = description;
    }

    // Print kitchen order details
    public void printService() {
        System.out.println("Kitchen Service Order: " + description);
        System.out.println("Chargable: " + isChargable);
        System.out.println("Requested at: " + requestTime);
    }
}