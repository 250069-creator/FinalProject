package org.example;

public class CreditCardTransaction extends BillTransaction {

    String nameOnCard;
    String zipCode;

    // Constructor with validation
    public CreditCardTransaction(double amount, String nameOnCard, String zipCode) {

        super(amount);

        // card holder name cannot be empty
        if (nameOnCard == null || nameOnCard.trim().isEmpty()) {
            throw new IllegalArgumentException("Card holder name cannot be empty!");
        }

        // zip code cannot be empty
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Zip code cannot be empty!");
        }

        this.nameOnCard = nameOnCard;
        this.zipCode    = zipCode;
    }

    @Override
    public boolean initiateTransaction() {
        System.out.println("Processing CREDIT CARD payment...");
        System.out.println("Card holder : " + nameOnCard);
        System.out.println("Amount      : $" + amount);
        this.status = PaymentStatus.COMPLETED;
        System.out.println("Credit card payment successful!");
        return true;
    }
}