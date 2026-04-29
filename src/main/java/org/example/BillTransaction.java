package org.example;

import java.util.Date;

public class BillTransaction {

    Date creationDate;
    double amount;
    PaymentStatus status;

    // Constructor with validation
    public BillTransaction(double amount) {

        // amount must be greater than zero
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero!");
        }

        this.amount       = amount;
        this.creationDate = new Date();
        this.status       = PaymentStatus.PENDING;
    }

    public boolean initiateTransaction() {
        System.out.println("Processing transaction of $" + amount);
        return true;
    }

    public void printTransaction() {
        System.out.println("Amount : $" + amount);
        System.out.println("Status : " + status);
        System.out.println("Date   : " + creationDate);
    }
}