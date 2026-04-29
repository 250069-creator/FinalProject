package org.example;

// Represents a user account in the system
public class Account {

    String id;
    String password;
    AccountStatus status;

    // Constructor
    public Account(String id, String password) {
        this.id = id;
        this.password = password;
        this.status = AccountStatus.ACTIVE; // new accounts start as active
    }

    // Reset the account password
    public boolean resetPassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password reset successfully for account: " + id);
        return true;
    }

    // Print account info
    public void printAccount() {
        System.out.println("Account ID: " + id);
        System.out.println("Status: " + status);
    }
}