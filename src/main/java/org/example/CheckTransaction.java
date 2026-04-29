package org.example;

public class CheckTransaction extends BillTransaction {

    String bankName;
    String checkNumber;

    // Constructor with validation
    public CheckTransaction(double amount, String bankName, String checkNumber) {

        super(amount);

        // bank name cannot be empty
        if (bankName == null || bankName.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be empty!");
        }

        // check number cannot be empty
        if (checkNumber == null || checkNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Check number cannot be empty!");
        }

        this.bankName     = bankName;
        this.checkNumber  = checkNumber;
    }

    @Override
    public boolean initiateTransaction() {
        System.out.println("Processing CHECK payment...");
        System.out.println("Bank         : " + bankName);
        System.out.println("Check number : " + checkNumber);
        System.out.println("Amount       : $" + amount);
        this.status = PaymentStatus.COMPLETED;
        System.out.println("Check payment successful!");
        return true;
    }
}