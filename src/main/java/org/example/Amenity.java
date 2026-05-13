package org.example;

public class Amenity {

    String name;
    String description;

    public Amenity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void printAmenity() {
        System.out.println("Amenity: " + name);
        System.out.println("Description: " + description);
    }
}