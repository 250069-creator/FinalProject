package org.example;

// Status of a payment
public enum PaymentStatus {
    UNPAID,
    PENDING,
    COMPLETED,
    FAILED,
    DECLINED,
    CANCELLED,
    ABANDONED,
    SETTLING,
    SETTLED,
    REFUNDED
}