package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class GuestPanel {

    private DatabaseManager db;

    public GuestPanel(DatabaseManager db) {
        this.db = db;
    }

    public VBox buildPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #fff8f0;");

        Label title = UIHelper.makeTitle("👤 Guest Management");

        TextField nameField  = UIHelper.makeInput("Full name...");
        TextField emailField = UIHelper.makeInput("Email address...");
        TextField phoneField = UIHelper.makeIntegerInput("Phone number (digits only)...");
        Label statusLabel    = new Label("");
        TextArea outputArea  = UIHelper.makeOutputArea();
        Button registerBtn   = UIHelper.makeButton("Register Guest", "#e65100");

        registerBtn.setOnAction(e -> handleRegisterGuest(
                nameField, emailField, phoneField,
                statusLabel, outputArea
        ));

        // records section
        TextArea recordsArea  = UIHelper.makeRecordsArea();
        Button viewGuestsBtn  = UIHelper.makeButton("View All Guests", "#6a1b9a");
        viewGuestsBtn.setOnAction(e -> loadRecords(recordsArea));

        panel.getChildren().addAll(
                title,
                UIHelper.makeLabel("Full Name:"),  UIHelper.centered(nameField),
                UIHelper.makeLabel("Email:"),       UIHelper.centered(emailField),
                UIHelper.makeLabel("Phone:"),       UIHelper.centered(phoneField),
                UIHelper.centered(registerBtn),     UIHelper.centered(statusLabel),
                UIHelper.centered(outputArea),
                new Separator(),
                UIHelper.makeTitle("📋 Guest Records"),
                UIHelper.centered(viewGuestsBtn),
                UIHelper.centered(recordsArea)
        );
        return panel;
    }

    private void handleRegisterGuest(TextField nameField,
                                     TextField emailField,
                                     TextField phoneField,
                                     Label statusLabel,
                                     TextArea outputArea) {
        if (nameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                phoneField.getText().isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Fill in all fields!");
            return;
        }

        try {
            Address address = new Address("N/A", "N/A", "N/A", "N/A", "N/A");
            Guest guest = new Guest(
                    nameField.getText(),
                    address,
                    emailField.getText(),
                    phoneField.getText()
            );

            db.saveGuest(
                    guest.name,
                    guest.email,
                    guest.phone,
                    guest.accountType.toString()
            );

            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Guest registered and saved!");
            outputArea.setText(
                    "Name: "  + guest.name        + "\n" +
                            "Email: " + guest.email        + "\n" +
                            "Phone: " + guest.phone        + "\n" +
                            "Type: "  + guest.accountType  + "\n" +
                            "💾 Saved to database!"
            );
            nameField.clear();
            emailField.clear();
            phoneField.clear();

        } catch (IllegalArgumentException ex) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ " + ex.getMessage());
        }
    }

    private void loadRecords(TextArea recordsArea) {
        var records = db.getAllGuests();
        if (records.isEmpty()) {
            recordsArea.setText("No guests registered yet.");
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