package org.example;

import java.util.Date;

public class Notification {

    String notificationId;
    String content;
    Date createdOn;

    public Notification(String notificationId, String content) {
        this.notificationId = notificationId;
        this.content = content;
        this.createdOn = new Date(); // automatically sets current date
    }

    public void send() {
        System.out.println("Sending notification...");
    }
}