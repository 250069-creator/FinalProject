package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

// Builds and manages the entire Notification panel
// Responsible for Email and Postal notification UI
public class NotificationPanel {

    // database manager passed in from Main
    private DatabaseManager db;

    // constructor - receives the shared database manager
    public NotificationPanel(DatabaseManager db) {
        this.db = db;
    }

    // ─────────────────────────────
    // Builds and returns the full notification panel
    // Called by Main.java to get the panel
    // ─────────────────────────────
    public VBox buildPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #f1fff6;");

        // EMAIL SECTION
        Label emailTitle      = UIHelper.makeTitle("📧 Email Notification");
        TextField emailField  = UIHelper.makeInput("Receiver email address...");
        TextArea emailMsgArea = UIHelper.makeMessageBox("Type your email message here...");
        Label emailStatus     = new Label("");
        Button sendEmailBtn   = UIHelper.makeButton("Send Email", "#388e3c");

        sendEmailBtn.setOnAction(e -> handleEmailSend(
                emailField, emailMsgArea, emailStatus
        ));

        Separator sep = new Separator();
        sep.setMaxWidth(400);

        // POSTAL SECTION
        Label postalTitle      = UIHelper.makeTitle("📬 Postal Notification");
        TextField addressField = UIHelper.makeInput("Receiver postal address...");
        TextArea postalMsgArea = UIHelper.makeMessageBox("Type your postal message here...");
        Label postalStatus     = new Label("");
        Button sendPostalBtn   = UIHelper.makeButton("Send Postal", "#1565c0");

        sendPostalBtn.setOnAction(e -> handlePostalSend(
                addressField, postalMsgArea, postalStatus
        ));

        // RECORDS SECTION
        Separator sep2        = new Separator();
        sep2.setMaxWidth(400);
        Label recordsTitle    = UIHelper.makeTitle("📋 Sent Notifications History");
        TextArea recordsArea  = UIHelper.makeRecordsArea();
        Button viewRecordsBtn = UIHelper.makeButton("View All Records", "#6a1b9a");

        viewRecordsBtn.setOnAction(e ->
                loadRecords(recordsArea)
        );

        // add everything to the panel
        panel.getChildren().addAll(
                emailTitle,
                UIHelper.makeLabel("Receiver Email:"),    UIHelper.centered(emailField),
                UIHelper.makeLabel("Message:"),            UIHelper.centered(emailMsgArea),
                UIHelper.centered(sendEmailBtn),           UIHelper.centered(emailStatus),
                sep,
                postalTitle,
                UIHelper.makeLabel("Receiver Address:"),  UIHelper.centered(addressField),
                UIHelper.makeLabel("Message:"),            UIHelper.centered(postalMsgArea),
                UIHelper.centered(sendPostalBtn),          UIHelper.centered(postalStatus),
                sep2,
                recordsTitle,
                UIHelper.centered(viewRecordsBtn),
                UIHelper.centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // Handles the Send Email button click
    // ─────────────────────────────
    private void handleEmailSend(TextField emailField,
                                 TextArea emailMsgArea,
                                 Label emailStatus) {
        // check empty fields
        if (emailField.getText().isEmpty() ||
                emailMsgArea.getText().isEmpty()) {
            showError(emailStatus, "❌ Please fill in all fields!");
            return;
        }

        // check valid email format
        if (!emailField.getText().contains("@")) {
            showError(emailStatus, "❌ Please enter a valid email address!");
            return;
        }

        try {
            // create EmailNotification object and send
            EmailNotification notif = new EmailNotification(
                    "N001",
                    emailMsgArea.getText(),
                    emailField.getText()
            );
            notif.send();

            // save to database
            db.saveNotification(
                    "EMAIL",
                    emailField.getText(),
                    emailMsgArea.getText(),
                    getCurrentDateTime()
            );

            showSuccess(emailStatus,
                    "✅ Email sent and saved! → " + emailField.getText());
            emailField.clear();
            emailMsgArea.clear();

        } catch (IllegalArgumentException ex) {
            showError(emailStatus, "❌ " + ex.getMessage());
        }
    }

    // ─────────────────────────────
    // Handles the Send Postal button click
    // ─────────────────────────────
    private void handlePostalSend(TextField addressField,
                                  TextArea postalMsgArea,
                                  Label postalStatus) {
        // check empty fields
        if (addressField.getText().isEmpty() ||
                postalMsgArea.getText().isEmpty()) {
            showError(postalStatus, "❌ Please fill in all fields!");
            return;
        }

        try {
            // create PostalNotification object and send
            PostalNotification notif = new PostalNotification(
                    "N002",
                    postalMsgArea.getText(),
                    addressField.getText()
            );
            notif.send();

            // save to database
            db.saveNotification(
                    "POSTAL",
                    addressField.getText(),
                    postalMsgArea.getText(),
                    getCurrentDateTime()
            );

            showSuccess(postalStatus,
                    "✅ Postal sent and saved! → " + addressField.getText());
            addressField.clear();
            postalMsgArea.clear();

        } catch (IllegalArgumentException ex) {
            showError(postalStatus, "❌ " + ex.getMessage());
        }
    }

    // ─────────────────────────────
    // Loads all notification records from database
    // ─────────────────────────────
    private void loadRecords(TextArea recordsArea) {
        var records = db.getAllNotifications();

        if (records.isEmpty()) {
            recordsArea.setText("No notifications sent yet.");
            return;
        }

        // build display text from all records
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++) {
            sb.append((i + 1))
                    .append(". ")
                    .append(records.get(i))
                    .append("\n\n");
        }
        recordsArea.setText(sb.toString());
    }

    // ─────────────────────────────
    // Shows red error message on a label
    // ─────────────────────────────
    private void showError(Label label, String message) {
        label.setStyle("-fx-text-fill: #c62828; -fx-font-size: 12px;");
        label.setText(message);
    }

    // ─────────────────────────────
    // Shows green success message on a label
    // ─────────────────────────────
    private void showSuccess(Label label, String message) {
        label.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 12px;");
        label.setText(message);
    }

    // ─────────────────────────────
    // Returns current date and time as string
    // ─────────────────────────────
    private String getCurrentDateTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}