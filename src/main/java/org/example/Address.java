package org.example;

// Simple address class used by Person and other classes
public class Address {

    String streetAddress;
    String city;
    String state;
    String zipcode;
    String country;

    // Constructor
    public Address(String streetAddress, String city, String state, String zipcode, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
    }

    // Returns full address as one string
    public String getFullAddress() {
        return streetAddress + ", " + city + ", " + state + ", " + zipcode + ", " + country;
    }
}
