package org.example;

import java.util.Date;

public class RoomHouseKeeping {

    String description;
    Date startDatetime;
    int duration;


    HouseKeeper houseKeeper;

    public RoomHouseKeeping(String description, int duration, HouseKeeper houseKeeper) {
        this.description = description;
        this.startDatetime = new Date();
        this.duration = duration;
        this.houseKeeper = houseKeeper;
    }

    public boolean addHouseKeeping(String newDescription) {
        this.description = newDescription;
        this.startDatetime = new Date();
        System.out.println("Housekeeping record updated: " + newDescription);
        return true;
    }

    public void printRecord() {
        System.out.println("Description: " + description);
        System.out.println("Started: " + startDatetime);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Done by: " + houseKeeper.name);
    }
}


