package org.example;

// Receptionist works at the hotel front desk
// Extends Person
public class Receptionist extends Person {

    // Constructor
    public Receptionist(String name, Address address, String email, String phone) {
        super(name, address, email, phone, AccountType.RECEPTIONIST);
    }

    // Receptionist can create bookings for guests
    public boolean createBooking() {
        System.out.println("Receptionist " + name + " is creating a booking...");
        System.out.println("Booking created by receptionist!");
        return true;
    }
}