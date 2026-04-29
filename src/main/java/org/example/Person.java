package org.example;

// Base class for all people in the system
// abstract means you cannot create a Person directly
// you must create Guest, Receptionist, etc.
public abstract class Person {

    // Common attributes every person has
    String name;
    Address address;
    String email;
    String phone;
    AccountType accountType;

    // Constructor
    public Person(String name, Address address, String email, String phone, AccountType accountType) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
    }

    // Simple method to print person info
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Account Type: " + accountType);
    }
}