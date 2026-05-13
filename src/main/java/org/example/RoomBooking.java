package org.example;

import java.util.Date;

public class RoomBooking {

    String reservationNumber;
    Date startDate;
    int durationInDays;
    BookingStatus status;
    Date checkin;
    Date checkout;
    Room room;
    Guest guest;


    public RoomBooking(String reservationNumber, Date startDate, int durationInDays, Room room, Guest guest) {

        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation number cannot be empty!");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be empty!");
        }

        if (durationInDays <= 0) {
            throw new IllegalArgumentException("Duration must be at least 1 day!");
        }

        if (durationInDays > 365) {
            throw new IllegalArgumentException("Duration cannot exceed 365 days!");
        }

        if (room == null) {
            throw new IllegalArgumentException("Room cannot be empty!");
        }

        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be empty!");
        }

        this.reservationNumber = reservationNumber;
        this.startDate         = startDate;
        this.durationInDays    = durationInDays;
        this.room              = room;
        this.guest             = guest;
        this.status            = BookingStatus.REQUESTED;
    }

    public boolean confirmBooking() {
        this.status = BookingStatus.CONFIRMED;
        System.out.println("Booking " + reservationNumber + " is CONFIRMED!");
        return true;
    }

    public boolean checkIn() {
        this.checkin = new Date();
        this.status  = BookingStatus.CHECKED_IN;
        room.status  = RoomStatus.OCCUPIED;
        System.out.println("Guest " + guest.name + " checked in to room " + room.roomNumber);
        return true;
    }

    public boolean checkOut() {
        this.checkout = new Date();
        this.status   = BookingStatus.CHECKED_OUT;
        room.status   = RoomStatus.AVAILABLE;
        System.out.println("Guest " + guest.name + " checked out from room " + room.roomNumber);
        return true;
    }

    public boolean cancelBooking() {
        this.status = BookingStatus.CANCELLED;
        room.status = RoomStatus.AVAILABLE;
        System.out.println("Booking " + reservationNumber + " has been CANCELLED.");
        return true;
    }

    public void fetchDetails() {
        System.out.println("=== Booking Details ===");
        System.out.println("Reservation : " + reservationNumber);
        System.out.println("Guest       : " + guest.name);
        System.out.println("Room        : " + room.roomNumber);
        System.out.println("Start Date  : " + startDate);
        System.out.println("Duration    : " + durationInDays + " days");
        System.out.println("Status      : " + status);
        System.out.println("=======================");
    }
}