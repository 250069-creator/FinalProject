package org.example;

public class Account {

    String id;
    String password;
    AccountStatus status;

    public Account(String id, String password) {
        this.id = id;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
    }

    public boolean resetPassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password reset successfully for account: " + id);
        return true;
    }

    public void printAccount() {
        System.out.println("Account ID: " + id);
        System.out.println("Status: " + status);
    }
}