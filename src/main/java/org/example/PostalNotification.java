package org.example;

public class PostalNotification extends Notification {

    String address;

    public PostalNotification(String notificationId, String content, String address) {
        super(notificationId, content);
        this.address = address;
    }

    @Override
    public void send() {
        System.out.println("Postal mail sent to " + address + ": " + content);
    }
}
