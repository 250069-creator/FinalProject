package org.example;

public class CashTransaction extends BillTransaction {

    double cashTendered;

    // Constructor with validation
    public CashTransaction(double amount, double cashTendered) {

        super(amount);

        // cash given must be greater than zero
        if (cashTendered <= 0) {
            throw new IllegalArgumentException("Cash tendered must be greater than zero!");
        }

        // cash given must be enough to cover the amount
        if (cashTendered < amount) {
            throw new IllegalArgumentException(
                    "Not enough cash! Amount due: $" + amount +
                            " but cash given: $" + cashTendered
            );
        }

        this.cashTendered = cashTendered;
    }

    @Override
    public boolean initiateTransaction() {
        System.out.println("Processing CASH payment...");
        System.out.println("Amount due  : $" + amount);
        System.out.println("Cash given  : $" + cashTendered);
        double change = cashTendered - amount;
        System.out.println("Change      : $" + change);
        this.status = PaymentStatus.COMPLETED;
        System.out.println("Cash payment successful!");
        return true;
    }
}