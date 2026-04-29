package org.example;

// HouseKeeper cleans and maintains rooms
// Extends Person
public class HouseKeeper extends Person {

    // Constructor
    public HouseKeeper(String name, Address address, String email, String phone) {
        super(name, address, email, phone, AccountType.MEMBER);
    }

    // Assign housekeeper to a room
    public boolean assignToRoom(String roomNumber) {
        System.out.println("HouseKeeper " + name + " assigned to room: " + roomNumber);
        return true;
    }
}