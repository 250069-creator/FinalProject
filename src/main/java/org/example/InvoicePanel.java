package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class InvoicePanel {

    private DatabaseManager db;

    private Invoice invoice = new Invoice();

    public InvoicePanel(DatabaseManager db) {
        this.db = db;
    }

    public VBox buildPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #fff5f5;");

        Label title = UIHelper.makeTitle("💳 Invoice & Payment");

        TextField amountField       = UIHelper.makeDecimalInput("Charge amount (e.g. 150.0)...");
        ComboBox<String> paymentBox = UIHelper.makeComboBox("Select payment method...",
                "Credit Card", "Cash", "Check");
        TextField extraField  = UIHelper.makeInput("Card holder name / Cash amount / Bank name...");
        Label statusLabel     = new Label("");
        TextArea outputArea   = UIHelper.makeOutputArea();
        Button payBtn         = UIHelper.makeButton("Process Payment", "#b71c1c");

        payBtn.setOnAction(e -> handlePayment(
                amountField, paymentBox, extraField,
                statusLabel, outputArea
        ));

        // records section
        TextArea recordsArea   = UIHelper.makeRecordsArea();
        Button viewInvoicesBtn = UIHelper.makeButton("View All Invoices", "#6a1b9a");
        viewInvoicesBtn.setOnAction(e -> loadRecords(recordsArea));

        panel.getChildren().addAll(
                title,
                UIHelper.makeLabel("Charge Amount ($):"), UIHelper.centered(amountField),
                UIHelper.makeLabel("Payment Method:"),     UIHelper.centered(paymentBox),
                UIHelper.makeLabel("Details:"),    UIHelper.centered(extraField),
                UIHelper.centered(payBtn),    UIHelper.centered(statusLabel),
                UIHelper.centered(outputArea),
                new Separator(),
                UIHelper.makeTitle("📋 Invoice Records"),
                UIHelper.centered(viewInvoicesBtn),
                UIHelper.centered(recordsArea)
        );
        return panel;
    }

    private void handlePayment(TextField amountField,
                               ComboBox<String> paymentBox,
                               TextField extraField,
                               Label statusLabel,
                               TextArea outputArea) {

        if (amountField.getText().isEmpty() ||
                paymentBox.getValue() == null ||
                extraField.getText().isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Fill in all fields!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountField.getText());
            String method = paymentBox.getValue();
            String extra  = extraField.getText();


            InvoiceItem item = new InvoiceItem(amount);
            invoice.addItem(item);

            BillTransaction transaction;
            if (method.equals("Credit Card")) {
                transaction = new CreditCardTransaction(amount, extra, "00000");
            } else if (method.equals("Cash")) {
                double cashGiven = Double.parseDouble(extra);
                transaction = new CashTransaction(amount, cashGiven);
            } else {
                transaction = new CheckTransaction(amount, extra, "CHK001");
            }
            transaction.initiateTransaction();

            db.saveInvoice(amount, method, extra, "COMPLETED");

            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Payment processed and saved!");
            outputArea.setText(
                    "Method: "         + method         + "\n" +
                            "Amount: $"        + amount         + "\n" +
                            "Invoice Total: $" + invoice.amount + "\n" +
                            "Status: COMPLETED\n" +
                            "💾 Saved to database!"
            );
            amountField.clear();
            extraField.clear();
            paymentBox.setValue(null);

        } catch (NumberFormatException ex) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ For Cash, enter a valid number in Details!");
        } catch (IllegalArgumentException ex) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ " + ex.getMessage());
        }
    }

    private void loadRecords(TextArea recordsArea) {
        var records = db.getAllInvoices();
        if (records.isEmpty()) {
            recordsArea.setText("No invoices processed yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(records.get(i)).append("\n\n");
        }
        recordsArea.setText(sb.toString());
    }
}