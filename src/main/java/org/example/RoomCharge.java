package org.example;

import java.util.Date;

public class RoomCharge {

    Date issuedAt;

    public RoomCharge() {
        this.issuedAt = new Date(); // current date
    }

    public boolean addInvoiceItem(Invoice invoice, double amount) {
        InvoiceItem item = new InvoiceItem(amount);
        invoice.addItem(item);
        System.out.println("Room charge of $" + amount + " added to invoice.");
        return true;
    }

    public void printCharge() {
        System.out.println("Charge issued at: " + issuedAt);
    }
}