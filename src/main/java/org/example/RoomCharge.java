package org.example;

import java.util.Date;

// RoomCharge represents a charge added to a guest's bill
// Example: room service charge, kitchen order charge
public class RoomCharge {

    // When this charge was issued
    Date issuedAt;

    // Constructor
    public RoomCharge() {
        this.issuedAt = new Date(); // current date
    }

    // Add this charge to an invoice
    public boolean addInvoiceItem(Invoice invoice, double amount) {
        InvoiceItem item = new InvoiceItem(amount);
        invoice.addItem(item);
        System.out.println("Room charge of $" + amount + " added to invoice.");
        return true;
    }

    // Print charge details
    public void printCharge() {
        System.out.println("Charge issued at: " + issuedAt);
    }
}