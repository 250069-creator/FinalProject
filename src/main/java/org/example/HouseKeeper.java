package org.example;

public class HouseKeeper extends Person {

    public HouseKeeper(String name, Address address, String email, String phone) {
        super(name, address, email, phone, AccountType.MEMBER);
    }

    public boolean assignToRoom(String roomNumber) {
        System.out.println("HouseKeeper " + name + " assigned to room: " + roomNumber);
        return true;
    }
}