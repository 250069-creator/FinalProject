package org.example;

import java.util.ArrayList;

// Represents one physical location/branch of the hotel
public class HotelLocation {

    String name;
    Address location;

    // List of all rooms in this location
    ArrayList<Room> rooms;

    // Constructor
    public HotelLocation(String name, Address location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
    }

    // Get all rooms in this location
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    // Add a room to this location
    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room " + room.roomNumber + " added to " + name);
    }

    // Print location details
    public void printLocation() {
        System.out.println("Location: " + name);
        System.out.println("Address: " + location.getFullAddress());
        System.out.println("Total Rooms: " + rooms.size());
    }
}