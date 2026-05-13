package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RoomPanel {

    private DatabaseManager db;

    public RoomPanel(DatabaseManager db) {
        this.db = db;
    }

    public VBox buildPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #f0f7ff;");

        Label title = UIHelper.makeTitle("🛏 Room Management");

        TextField roomNumberField = UIHelper.makeInput("Room number (e.g. 101)...");
        ComboBox<String> styleBox = UIHelper.makeComboBox("Select room style...",
                "STANDARD", "DELUXE", "FAMILY_SUITE", "BUSINESS_SUITE");
        TextField priceField  = UIHelper.makeDecimalInput("Price per night (e.g. 150.0)...");
        Label statusLabel     = new Label("");
        TextArea outputArea   = UIHelper.makeOutputArea();
        Button addRoomBtn     = UIHelper.makeButton("Add Room", "#1565c0");

        addRoomBtn.setOnAction(e -> handleAddRoom(
                roomNumberField, styleBox, priceField,
                statusLabel, outputArea
        ));

        // records section
        TextArea recordsArea = UIHelper.makeRecordsArea();
        Button viewRoomsBtn  = UIHelper.makeButton("View All Rooms", "#6a1b9a");
        viewRoomsBtn.setOnAction(e -> loadRecords(recordsArea));

        panel.getChildren().addAll(
                title,
                UIHelper.makeLabel("Room Number:"),         UIHelper.centered(roomNumberField),
                UIHelper.makeLabel("Room Style:"),          UIHelper.centered(styleBox),
                UIHelper.makeLabel("Price Per Night ($):"), UIHelper.centered(priceField),
                UIHelper.centered(addRoomBtn),              UIHelper.centered(statusLabel),
                UIHelper.centered(outputArea),
                new Separator(),
                UIHelper.makeTitle("📋 Room Records"),
                UIHelper.centered(viewRoomsBtn),
                UIHelper.centered(recordsArea)
        );
        return panel;
    }

    private void handleAddRoom(TextField roomNumberField,
                               ComboBox<String> styleBox,
                               TextField priceField,
                               Label statusLabel,
                               TextArea outputArea) {

        if (roomNumberField.getText().isEmpty() ||
                styleBox.getValue() == null ||
                priceField.getText().isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Fill in all fields!");
            return;
        }

        try {
            RoomStyle style = RoomStyle.valueOf(styleBox.getValue());
            double price    = Double.parseDouble(priceField.getText());
            Room room       = new Room(roomNumberField.getText(), style, price, false);

            db.saveRoom(
                    room.roomNumber,
                    room.style.toString(),
                    room.bookingPrice,
                    room.status.toString()
            );

            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Room added and saved!");
            outputArea.setText(
                    "Room: "   + room.roomNumber   + "\n" +
                            "Style: "  + room.style        + "\n" +
                            "Price: $" + room.bookingPrice + "/night\n" +
                            "Status: " + room.status       + "\n" +
                            "💾 Saved to database!"
            );
            roomNumberField.clear();
            priceField.clear();
            styleBox.setValue(null);

        } catch (IllegalArgumentException ex) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ " + ex.getMessage());
        }
    }

    private void loadRecords(TextArea recordsArea) {
        var records = db.getAllRooms();
        if (records.isEmpty()) {
            recordsArea.setText("No rooms added yet.");
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
