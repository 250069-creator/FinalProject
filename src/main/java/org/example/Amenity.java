package org.example;

// Represents an amenity a guest can request
// Example: towels, toiletries, extra pillows
public class Amenity {

    String name;
    String description;

    // Constructor
    public Amenity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Print amenity details
    public void printAmenity() {
        System.out.println("Amenity: " + name);
        System.out.println("Description: " + description);
    }
}