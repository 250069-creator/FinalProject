package org.example;

import java.util.Date;

// Represents an electronic key card for a room
public class RoomKey {

    String keyId;       // unique key ID
    String barcode;     // barcode on the card
    Date issuedAt;      // when the key was given
    boolean active;     // is the key currently working
    boolean isMaster;   // is this a master key

    // Constructor
    public RoomKey(String keyId, String barcode, boolean isMaster) {
        this.keyId = keyId;
        this.barcode = barcode;
        this.issuedAt = new Date(); // issued right now
        this.active = true;         // active when created
        this.isMaster = isMaster;
    }

    // Assign this key to a room
    public boolean assignRoom(String roomNumber) {
        System.out.println("Key " + keyId + " assigned to room: " + roomNumber);
        return true;
    }

    // Check if this key is still active
    public boolean isActive() {
        return active;
    }

    // Deactivate the key when guest checks out
    public void deactivate() {
        this.active = false;
        System.out.println("Key " + keyId + " has been deactivated.");
    }

    // Print key details
    public void printKey() {
        System.out.println("Key ID: " + keyId);
        System.out.println("Barcode: " + barcode);
        System.out.println("Active: " + active);
        System.out.println("Master Key: " + isMaster);
        System.out.println("Issued At: " + issuedAt);
    }
}