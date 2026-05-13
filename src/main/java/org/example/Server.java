package org.example;

public class Server extends Person {

    public Server(String name, Address address, String email, String phone) {
        super(name, address, email, phone, AccountType.MEMBER);
    }

    public boolean addRoomCharge(String roomNumber, double chargeAmount) {
        System.out.println("Server " + name + " adding charge of $" + chargeAmount);
        System.out.println("Charge added to room: " + roomNumber);
        return true;
    }
}