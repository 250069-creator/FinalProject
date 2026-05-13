package org.example;

import java.util.Date;

public class RoomKey {

    String keyId;
    String barcode;
    Date issuedAt;
    boolean active;
    boolean isMaster;

    // Constructor
    public RoomKey(String keyId, String barcode, boolean isMaster) {
        this.keyId = keyId;
        this.barcode = barcode;
        this.issuedAt = new Date();
        this.active = true;
        this.isMaster = isMaster;
    }

    public boolean assignRoom(String roomNumber) {
        System.out.println("Key " + keyId + " assigned to room: " + roomNumber);
        return true;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
        System.out.println("Key " + keyId + " has been deactivated.");
    }

    public void printKey() {
        System.out.println("Key ID: " + keyId);
        System.out.println("Barcode: " + barcode);
        System.out.println("Active: " + active);
        System.out.println("Master Key: " + isMaster);
        System.out.println("Issued At: " + issuedAt);
    }
}