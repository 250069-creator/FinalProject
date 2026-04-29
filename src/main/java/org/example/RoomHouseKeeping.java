package org.example;

import java.util.Date;

// Keeps track of housekeeping tasks for a room
// Example: cleaning log, maintenance records
public class RoomHouseKeeping {

    String description;    // what was done
    Date startDatetime;    // when it started
    int duration;          // how long it took in minutes

    // The housekeeper who did the work
    HouseKeeper houseKeeper;

    // Constructor
    public RoomHouseKeeping(String description, int duration, HouseKeeper houseKeeper) {
        this.description = description;
        this.startDatetime = new Date(); // starts now
        this.duration = duration;
        this.houseKeeper = houseKeeper;
    }

    // Add a new housekeeping record
    public boolean addHouseKeeping(String newDescription) {
        this.description = newDescription;
        this.startDatetime = new Date();
        System.out.println("Housekeeping record updated: " + newDescription);
        return true;
    }

    // Print housekeeping record
    public void printRecord() {
        System.out.println("Description: " + description);
        System.out.println("Started: " + startDatetime);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Done by: " + houseKeeper.name);
    }
}


