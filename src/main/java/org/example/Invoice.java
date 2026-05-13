package org.example;

import java.util.ArrayList;

public class Invoice {

    double amount;
    ArrayList<InvoiceItem> items;

    public Invoice() {
        this.amount = 0;
        this.items  = new ArrayList<>();
    }

    public void addItem(InvoiceItem item) {

        if (item == null) {
            throw new IllegalArgumentException("Invoice item cannot be null!");
        }

        if (item.amount <= 0) {
            throw new IllegalArgumentException("Invoice item amount must be greater than zero!");
        }

        items.add(item);
        amount += item.amount;
        System.out.println("Item added. New total: $" + amount);
    }

    public boolean createBill() {
        System.out.println("===== INVOICE =====");
        for (InvoiceItem item : items) {
            item.printItem();
        }
        System.out.println("Total Amount: $" + amount);
        System.out.println("===================");
        return true;
    }
}