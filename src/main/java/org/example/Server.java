package org.example;

// Server handles room service for guests
// Extends Person
public class Server extends Person {

    // Constructor
    public Server(String name, Address address, String email, String phone) {
        super(name, address, email, phone, AccountType.MEMBER);
    }

    // Add a room service charge to a guest
    public boolean addRoomCharge(String roomNumber, double chargeAmount) {
        System.out.println("Server " + name + " adding charge of $" + chargeAmount);
        System.out.println("Charge added to room: " + roomNumber);
        return true;
    }
}