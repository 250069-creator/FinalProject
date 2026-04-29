package org.example;

public class Guest extends Person {

    int totalRoomsCheckedIn;

    // Constructor with validation
    public Guest(String name, Address address, String email, String phone) {

        super(name, address, email, phone, AccountType.GUEST);

        // name cannot be empty
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty!");
        }

        // email must contain @ and a dot
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address! Must contain @ and .");
        }

        // phone must contain only digits
        if (phone == null || !phone.matches("[0-9]+")) {
            throw new IllegalArgumentException("Phone number must contain digits only!");
        }

        // phone must be at least 7 digits
        if (phone.length() < 7) {
            throw new IllegalArgumentException("Phone number must be at least 7 digits!");
        }

        // phone cannot be more than 15 digits
        if (phone.length() > 15) {
            throw new IllegalArgumentException("Phone number cannot be more than 15 digits!");
        }

        this.totalRoomsCheckedIn = 0;
    }

    public boolean createBooking() {
        System.out.println("Guest " + name + " is creating a booking...");
        totalRoomsCheckedIn++;
        System.out.println("Booking created successfully!");
        return true;
    }
}