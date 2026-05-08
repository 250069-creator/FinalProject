package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

// Builds and manages the Room Booking panel
public class BookingPanel {

    private DatabaseManager db;

    public BookingPanel(DatabaseManager db) {
        this.db = db;
    }

    public VBox buildPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #faf5ff;");

        Label title = UIHelper.makeTitle("📋 Room Booking");

        TextField reservationField = UIHelper.makeInput("Reservation number (e.g. R001)...");
        TextField guestNameField   = UIHelper.makeInput("Guest name...");
        TextField roomNumberField  = UIHelper.makeInput("Room number (e.g. 101)...");
        TextField durationField    = UIHelper.makeIntegerInput("Duration in days (e.g. 3)...");
        Label statusLabel          = new Label("");
        TextArea outputArea        = UIHelper.makeOutputArea();
        Button bookBtn             = UIHelper.makeButton("Create Booking", "#6a1b9a");

        bookBtn.setOnAction(e -> handleCreateBooking(
                reservationField, guestNameField,
                roomNumberField, durationField,
                statusLabel, outputArea
        ));

        // records section
        TextArea recordsArea    = UIHelper.makeRecordsArea();
        Button viewBookingsBtn  = UIHelper.makeButton("View All Bookings", "#6a1b9a");
        viewBookingsBtn.setOnAction(e -> loadRecords(recordsArea));

        panel.getChildren().addAll(
                title,
                UIHelper.makeLabel("Reservation Number:"), UIHelper.centered(reservationField),
                UIHelper.makeLabel("Guest Name:"),         UIHelper.centered(guestNameField),
                UIHelper.makeLabel("Room Number:"),        UIHelper.centered(roomNumberField),
                UIHelper.makeLabel("Duration (days):"),    UIHelper.centered(durationField),
                UIHelper.centered(bookBtn),                UIHelper.centered(statusLabel),
                UIHelper.centered(outputArea),
                new Separator(),
                UIHelper.makeTitle("📋 Booking Records"),
                UIHelper.centered(viewBookingsBtn),
                UIHelper.centered(recordsArea)
        );
        return panel;
    }

    private void handleCreateBooking(TextField reservationField,
                                     TextField guestNameField,
                                     TextField roomNumberField,
                                     TextField durationField,
                                     Label statusLabel,
                                     TextArea outputArea) {
        // check empty fields
        if (reservationField.getText().isEmpty() ||
                guestNameField.getText().isEmpty() ||
                roomNumberField.getText().isEmpty() ||
                durationField.getText().isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Fill in all fields!");
            return;
        }

        try {
            int duration = Integer.parseInt(durationField.getText());

            // validate duration range
            if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be at least 1 day!");
            }
            if (duration > 365) {
                throw new IllegalArgumentException("Duration cannot exceed 365 days!");
            }

            // save to database
            db.saveBooking(
                    reservationField.getText(),
                    guestNameField.getText(),
                    roomNumberField.getText(),
                    duration,
                    "CONFIRMED"
            );

            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Booking created and saved!");
            outputArea.setText(
                    "Reservation: " + reservationField.getText() + "\n" +
                            "Guest: "       + guestNameField.getText()   + "\n" +
                            "Room: "        + roomNumberField.getText()  + "\n" +
                            "Duration: "    + durationField.getText()    + " days\n" +
                            "Status: CONFIRMED\n" +
                            "💾 Saved to database!"
            );
            reservationField.clear();
            guestNameField.clear();
            roomNumberField.clear();
            durationField.clear();

        } catch (IllegalArgumentException ex) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ " + ex.getMessage());
        }
    }

    private void loadRecords(TextArea recordsArea) {
        var records = db.getAllBookings();
        if (records.isEmpty()) {
            recordsArea.setText("No bookings created yet.");
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
