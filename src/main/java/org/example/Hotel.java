package org.example;

import java.util.ArrayList;

// Hotel is the main class - top of the hierarchy
// One hotel can have many locations
public class Hotel {

    String name;

    // List of all hotel locations/branches
    ArrayList<HotelLocation> locations;

    // Constructor
    public Hotel(String name) {
        this.name = name;
        this.locations = new ArrayList<>();
    }

    // Add a new location to the hotel
    public boolean addLocation(HotelLocation location) {
        locations.add(location);
        System.out.println("New location added to " + name + ": " + location.name);
        return true;
    }

    // Print all hotel info
    public void printHotel() {
        System.out.println("====== HOTEL: " + name + " ======");
        System.out.println("Total Locations: " + locations.size());
        for (HotelLocation loc : locations) {
            loc.printLocation();
        }
    }
}