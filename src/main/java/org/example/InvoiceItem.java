package org.example;

public class InvoiceItem {

    double amount;

    // Constructor with validation
    public InvoiceItem(double amount) {

        // amount must be greater than zero
        if (amount <= 0) {
            throw new IllegalArgumentException("Invoice item amount must be greater than zero!");
        }

        this.amount = amount;
    }

    public boolean updateAmount(double newAmount) {

        // new amount must also be greater than zero
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Updated amount must be greater than zero!");
        }

        this.amount = newAmount;
        System.out.println("Amount updated to: $" + newAmount);
        return true;
    }

    public void printItem() {
        System.out.println("Item amount: $" + amount);
    }
}