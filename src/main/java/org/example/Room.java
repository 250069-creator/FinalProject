package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Room implements Search {

    String roomNumber;
    RoomStyle style;
    RoomStatus status;
    double bookingPrice;
    boolean isSmoking;
    RoomKey roomKey;

    // Constructor with validation
    public Room(String roomNumber, RoomStyle style, double bookingPrice, boolean isSmoking) {

        // room number cannot be empty
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty!");
        }

        // price must be greater than zero
        if (bookingPrice <= 0) {
            throw new IllegalArgumentException("Booking price must be greater than zero!");
        }

        this.roomNumber   = roomNumber;
        this.style        = style;
        this.bookingPrice = bookingPrice;
        this.isSmoking    = isSmoking;
        this.status       = RoomStatus.AVAILABLE;
    }

    public boolean isRoomAvailable() {
        return status == RoomStatus.AVAILABLE;
    }

    public boolean checkIn() {
        if (isRoomAvailable()) {
            this.status = RoomStatus.OCCUPIED;
            System.out.println("Room " + roomNumber + " is now OCCUPIED.");
            return true;
        }
        System.out.println("Room " + roomNumber + " is NOT available!");
        return false;
    }

    public boolean checkOut() {
        this.status = RoomStatus.AVAILABLE;
        System.out.println("Room " + roomNumber + " is now AVAILABLE.");
        return true;
    }

    @Override
    public ArrayList<Room> searchRoom(RoomStyle style, Date startDate, int durationDays) {
        ArrayList<Room> results = new ArrayList<>();
        if (this.style == style && this.isRoomAvailable()) {
            results.add(this);
        }
        return results;
    }

    public void printRoom() {
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Style       : " + style);
        System.out.println("Price       : $" + bookingPrice + " per night");
        System.out.println("Status      : " + status);
        System.out.println("Smoking     : " + isSmoking);
    }
}