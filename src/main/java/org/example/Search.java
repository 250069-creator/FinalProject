package org.example;

import java.util.Date;
import java.util.ArrayList;

// Search is an interface - defines what search must do
// Room and other classes implement this
public interface Search {

    // Search for available rooms by style and dates
    ArrayList<Room> searchRoom(RoomStyle style, Date startDate, int durationDays);
}