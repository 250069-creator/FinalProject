package org.example;

import java.util.ArrayList;

public class Hotel {

    String name;

    ArrayList<HotelLocation> locations;

    public Hotel(String name) {
        this.name = name;
        this.locations = new ArrayList<>();
    }

    public boolean addLocation(HotelLocation location) {
        locations.add(location);
        System.out.println("New location added to " + name + ": " + location.name);
        return true;
    }


    public void printHotel() {
        System.out.println("====== HOTEL: " + name + " ======");
        System.out.println("Total Locations: " + locations.size());
        for (HotelLocation loc : locations) {
            loc.printLocation();
        }
    }
}