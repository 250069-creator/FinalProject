package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    // Create ONE database manager used by the whole application
    // All panels share the same database connection
    DatabaseManager db = new DatabaseManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Connect to database and create tables when app starts
        db.connect();
        db.createTables();

        // Title
        Label titleLabel = new Label("🏨  GRAND HOTEL");
        titleLabel.setStyle(
                "-fx-font-size: 26px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f;"
        );

        Label subtitleLabel = new Label("Management System");
        subtitleLabel.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-text-fill: #7a6a5a;"
        );

        VBox titleStack = new VBox(3, titleLabel, subtitleLabel);
        titleStack.setAlignment(Pos.CENTER);

        HBox titleBox = new HBox(titleStack);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(18));
        titleBox.setStyle("-fx-background-color: #fdf3e3; " +
                "-fx-border-color: #e8d5b7; -fx-border-width: 0 0 2 0;");

        // Nav buttons
        Button notifBtn   = new Button("🔔 Notifications");
        Button roomBtn    = new Button("🛏 Rooms");
        Button guestBtn   = new Button("👤 Guests");
        Button bookingBtn = new Button("📋 Bookings");
        Button invoiceBtn = new Button("💳 Invoice");

        String defaultNav =
                "-fx-pref-width: 140px; -fx-pref-height: 38px; " +
                        "-fx-font-size: 12px; -fx-font-weight: bold; " +
                        "-fx-background-color: #e8e0d5; -fx-text-fill: #5a4a3a; " +
                        "-fx-background-radius: 6px; -fx-cursor: hand;";

        notifBtn.setStyle(defaultNav);
        roomBtn.setStyle(defaultNav);
        guestBtn.setStyle(defaultNav);
        bookingBtn.setStyle(defaultNav);
        invoiceBtn.setStyle(defaultNav);

        HBox navBar = new HBox(8, notifBtn, roomBtn, guestBtn,
                bookingBtn, invoiceBtn);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #f5ede0;");

        // Build all panels - pass db so each panel can save to database
        VBox notifPanel   = buildNotificationPanel();
        VBox roomPanel    = buildRoomPanel();
        VBox guestPanel   = buildGuestPanel();
        VBox bookingPanel = buildBookingPanel();
        VBox invoicePanel = buildInvoicePanel();

        ScrollPane scrollPane = new ScrollPane(notifPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background: transparent;");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(new VBox(titleBox, navBar));
        mainLayout.setCenter(scrollPane);
        mainLayout.setStyle("-fx-background-color: #fffaf4;");

        // Nav button actions
        notifBtn.setOnAction(e -> {
            scrollPane.setContent(notifPanel);
            resetButtons(defaultNav, notifBtn, roomBtn,
                    guestBtn, bookingBtn, invoiceBtn);
            notifBtn.setStyle(defaultNav
                    .replace("#e8e0d5", "#a8d5a2")
                    .replace("#5a4a3a", "#1b5e20")
                    + "-fx-text-fill: #1b5e20;");
        });

        roomBtn.setOnAction(e -> {
            scrollPane.setContent(roomPanel);
            resetButtons(defaultNav, notifBtn, roomBtn,
                    guestBtn, bookingBtn, invoiceBtn);
            roomBtn.setStyle(defaultNav
                    .replace("#e8e0d5", "#a3c4f3")
                    .replace("#5a4a3a", "#0d3b8e")
                    + "-fx-text-fill: #0d3b8e;");
        });

        guestBtn.setOnAction(e -> {
            scrollPane.setContent(guestPanel);
            resetButtons(defaultNav, notifBtn, roomBtn,
                    guestBtn, bookingBtn, invoiceBtn);
            guestBtn.setStyle(defaultNav
                    .replace("#e8e0d5", "#ffd8a8")
                    .replace("#5a4a3a", "#7a3e00")
                    + "-fx-text-fill: #7a3e00;");
        });

        bookingBtn.setOnAction(e -> {
            scrollPane.setContent(bookingPanel);
            resetButtons(defaultNav, notifBtn, roomBtn,
                    guestBtn, bookingBtn, invoiceBtn);
            bookingBtn.setStyle(defaultNav
                    .replace("#e8e0d5", "#d4b8f0")
                    .replace("#5a4a3a", "#4a148c")
                    + "-fx-text-fill: #4a148c;");
        });

        invoiceBtn.setOnAction(e -> {
            scrollPane.setContent(invoicePanel);
            resetButtons(defaultNav, notifBtn, roomBtn,
                    guestBtn, bookingBtn, invoiceBtn);
            invoiceBtn.setStyle(defaultNav
                    .replace("#e8e0d5", "#f5b8b8")
                    .replace("#5a4a3a", "#7f0000")
                    + "-fx-text-fill: #7f0000;");
        });

        // Close database connection when window is closed
        stage.setOnCloseRequest(e -> db.disconnect());

        Scene scene = new Scene(mainLayout, 680, 520);
        stage.setTitle("Grand Hotel Management System");
        stage.setScene(scene);
        stage.show();
    }

    // ─────────────────────────────
    // HELPER: standard input field
    // ─────────────────────────────
    private TextField makeInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );
        return field;
    }

    // ─────────────────────────────
    // HELPER: decimal number only
    // ─────────────────────────────
    private TextField makeDecimalInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*\\.?[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }

    // ─────────────────────────────
    // HELPER: whole number only
    // ─────────────────────────────
    private TextField makeIntegerInput(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setMaxWidth(380);
        field.setPrefWidth(380);
        field.setStyle(
                "-fx-pref-height: 36px; -fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[0-9]*")) {
                field.setText(oldVal);
            }
        });
        return field;
    }

    // ─────────────────────────────
    // HELPER: message text area
    // ─────────────────────────────
    private TextArea makeMessageBox(String placeholder) {
        TextArea area = new TextArea();
        area.setPromptText(placeholder);
        area.setMaxWidth(380);
        area.setPrefWidth(380);
        area.setPrefHeight(100);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #e8f4fd; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #a3c4e8; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #1a1a2e;"
        );
        return area;
    }

    // ─────────────────────────────
    // HELPER: output area
    // ─────────────────────────────
    private TextArea makeOutputArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setMaxWidth(380);
        area.setPrefWidth(380);
        area.setPrefHeight(120);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #f0fff4; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #a5d6a7; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #1b3a1f;"
        );
        return area;
    }

    // ─────────────────────────────
    // HELPER: records display area (bigger)
    // ─────────────────────────────
    private TextArea makeRecordsArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setMaxWidth(560);
        area.setPrefWidth(560);
        area.setPrefHeight(180);
        area.setWrapText(true);
        area.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c; " +
                        "-fx-font-family: monospace;"
        );
        return area;
    }

    // ─────────────────────────────
    // HELPER: ComboBox
    // ─────────────────────────────
    private ComboBox<String> makeComboBox(String prompt, String... items) {
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll(items);
        box.setPromptText(prompt);
        box.setMaxWidth(380);
        box.setPrefWidth(380);
        box.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #fce4ec; " +
                        "-fx-border-color: #e5a0b5; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-background-radius: 6px;"
        );
        return box;
    }

    // ─────────────────────────────
    // HELPER: action button
    // ─────────────────────────────
    private Button makeButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 9px 28px; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setOpacity(0.85));
        btn.setOnMouseExited(e -> btn.setOpacity(1.0));
        return btn;
    }

    // ─────────────────────────────
    // HELPER: section title
    // ─────────────────────────────
    private Label makeTitle(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f; -fx-padding: 0 0 10 0;"
        );
        return lbl;
    }

    // ─────────────────────────────
    // HELPER: field label
    // ─────────────────────────────
    private Label makeLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #4a3728;"
        );
        return lbl;
    }

    // ─────────────────────────────
    // HELPER: center wrap a node
    // ─────────────────────────────
    private HBox centered(javafx.scene.Node node) {
        HBox box = new HBox(node);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }

    // ─────────────────────────────
    // HELPER: get current date and time as string
    // ─────────────────────────────
    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // ─────────────────────────────
    // PANEL: Notifications
    // ─────────────────────────────
    private VBox buildNotificationPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #f1fff6;");

        // EMAIL SECTION
        Label emailTitle      = makeTitle("📧 Email Notification");
        TextField emailField  = makeInput("Receiver email address...");
        TextArea emailMsgArea = makeMessageBox("Type your email message here...");
        Label emailStatus     = new Label("");
        Button sendEmailBtn   = makeButton("Send Email", "#388e3c");

        sendEmailBtn.setOnAction(e -> {
            if (emailField.getText().isEmpty() ||
                    emailMsgArea.getText().isEmpty()) {
                emailStatus.setStyle(
                        "-fx-text-fill: #c62828; -fx-font-size: 12px;");
                emailStatus.setText("❌ Please fill in all fields!");
                return;
            }
            if (!emailField.getText().contains("@")) {
                emailStatus.setStyle(
                        "-fx-text-fill: #c62828; -fx-font-size: 12px;");
                emailStatus.setText("❌ Please enter a valid email address!");
                return;
            }
            try {
                EmailNotification notif = new EmailNotification(
                        "N001", emailMsgArea.getText(), emailField.getText()
                );
                notif.send();

                // ✅ SAVE TO DATABASE
                db.saveNotification(
                        "EMAIL",
                        emailField.getText(),
                        emailMsgArea.getText(),
                        getCurrentDateTime()
                );

                emailStatus.setStyle(
                        "-fx-text-fill: #2e7d32; -fx-font-size: 12px;");
                emailStatus.setText(
                        "✅ Email sent and saved! → " + emailField.getText());
                emailField.clear();
                emailMsgArea.clear();

            } catch (IllegalArgumentException ex) {
                emailStatus.setStyle(
                        "-fx-text-fill: #c62828; -fx-font-size: 12px;");
                emailStatus.setText("❌ " + ex.getMessage());
            }
        });

        Separator sep = new Separator();
        sep.setMaxWidth(400);

        // POSTAL SECTION
        Label postalTitle      = makeTitle("📬 Postal Notification");
        TextField addressField = makeInput("Receiver postal address...");
        TextArea postalMsgArea = makeMessageBox("Type your postal message here...");
        Label postalStatus     = new Label("");
        Button sendPostalBtn   = makeButton("Send Postal", "#1565c0");

        sendPostalBtn.setOnAction(e -> {
            if (addressField.getText().isEmpty() ||
                    postalMsgArea.getText().isEmpty()) {
                postalStatus.setStyle(
                        "-fx-text-fill: #c62828; -fx-font-size: 12px;");
                postalStatus.setText("❌ Please fill in all fields!");
                return;
            }
            try {
                PostalNotification notif = new PostalNotification(
                        "N002", postalMsgArea.getText(), addressField.getText()
                );
                notif.send();

                // ✅ SAVE TO DATABASE
                db.saveNotification(
                        "POSTAL",
                        addressField.getText(),
                        postalMsgArea.getText(),
                        getCurrentDateTime()
                );

                postalStatus.setStyle(
                        "-fx-text-fill: #2e7d32; -fx-font-size: 12px;");
                postalStatus.setText(
                        "✅ Postal sent and saved! → " + addressField.getText());
                addressField.clear();
                postalMsgArea.clear();

            } catch (IllegalArgumentException ex) {
                postalStatus.setStyle(
                        "-fx-text-fill: #c62828; -fx-font-size: 12px;");
                postalStatus.setText("❌ " + ex.getMessage());
            }
        });

        Separator sep2 = new Separator();
        sep2.setMaxWidth(400);

        // VIEW RECORDS SECTION
        Label recordsTitle  = makeTitle("📋 Sent Notifications History");
        TextArea recordsArea = makeRecordsArea();
        Button viewRecordsBtn = makeButton("View All Records", "#6a1b9a");

        viewRecordsBtn.setOnAction(e -> {
            // get all notifications from database
            var records = db.getAllNotifications();

            if (records.isEmpty()) {
                recordsArea.setText("No notifications sent yet.");
                return;
            }

            // build display string from all records
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < records.size(); i++) {
                sb.append((i + 1)).append(". ").append(records.get(i)).append("\n\n");
            }
            recordsArea.setText(sb.toString());
        });

        panel.getChildren().addAll(
                emailTitle,
                makeLabel("Receiver Email:"),    centered(emailField),
                makeLabel("Message:"),            centered(emailMsgArea),
                centered(sendEmailBtn),           centered(emailStatus),
                sep,
                postalTitle,
                makeLabel("Receiver Address:"),  centered(addressField),
                makeLabel("Message:"),            centered(postalMsgArea),
                centered(sendPostalBtn),          centered(postalStatus),
                sep2,
                recordsTitle,
                centered(viewRecordsBtn),
                centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // PANEL: Rooms
    // ─────────────────────────────
    private VBox buildRoomPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #f0f7ff;");

        Label title = makeTitle("🛏 Room Management");

        TextField roomNumberField = makeInput("Room number (e.g. 101)...");
        ComboBox<String> styleBox = makeComboBox("Select room style...",
                "STANDARD", "DELUXE", "FAMILY_SUITE", "BUSINESS_SUITE");
        TextField priceField  = makeDecimalInput("Price per night (e.g. 150.0)...");
        Label statusLabel     = new Label("");
        TextArea outputArea   = makeOutputArea();
        Button addRoomBtn     = makeButton("Add Room", "#1565c0");

        addRoomBtn.setOnAction(e -> {
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
                Room room       = new Room(
                        roomNumberField.getText(), style, price, false);

                // ✅ SAVE TO DATABASE
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
        });

        // VIEW RECORDS
        TextArea recordsArea   = makeRecordsArea();
        Button viewRoomsBtn    = makeButton("View All Rooms", "#6a1b9a");

        viewRoomsBtn.setOnAction(e -> {
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
        });

        panel.getChildren().addAll(
                title,
                makeLabel("Room Number:"),
                centered(roomNumberField),
                makeLabel("Room Style:"),
                centered(styleBox),
                makeLabel("Price Per Night ($):"),
                centered(priceField),
                centered(addRoomBtn),
                centered(statusLabel),
                centered(outputArea),
                new Separator(),
                makeTitle("📋 Room Records"),
                centered(viewRoomsBtn),
                centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // PANEL: Guests
    // ─────────────────────────────
    private VBox buildGuestPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #fff8f0;");

        Label title = makeTitle("👤 Guest Management");

        TextField nameField  = makeInput("Full name...");
        TextField emailField = makeInput("Email address...");
        TextField phoneField = makeIntegerInput("Phone number (digits only)...");
        Label statusLabel    = new Label("");
        TextArea outputArea  = makeOutputArea();
        Button registerBtn   = makeButton("Register Guest", "#e65100");

        registerBtn.setOnAction(e -> {
            if (nameField.getText().isEmpty() ||
                    emailField.getText().isEmpty() ||
                    phoneField.getText().isEmpty()) {
                statusLabel.setStyle("-fx-text-fill: #c62828;");
                statusLabel.setText("❌ Fill in all fields!");
                return;
            }
            try {
                Address address = new Address(
                        "N/A", "N/A", "N/A", "N/A", "N/A");
                Guest guest = new Guest(
                        nameField.getText(),
                        address,
                        emailField.getText(),
                        phoneField.getText()
                );

                // ✅ SAVE TO DATABASE
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
        });

        // VIEW RECORDS
        TextArea recordsArea  = makeRecordsArea();
        Button viewGuestsBtn  = makeButton("View All Guests", "#6a1b9a");

        viewGuestsBtn.setOnAction(e -> {
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
        });

        panel.getChildren().addAll(
                title,
                makeLabel("Full Name:"),  centered(nameField),
                makeLabel("Email:"),      centered(emailField),
                makeLabel("Phone:"),      centered(phoneField),
                centered(registerBtn),    centered(statusLabel),
                centered(outputArea),
                new Separator(),
                makeTitle("📋 Guest Records"),
                centered(viewGuestsBtn),
                centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // PANEL: Bookings
    // ─────────────────────────────
    private VBox buildBookingPanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #faf5ff;");

        Label title = makeTitle("📋 Room Booking");

        TextField reservationField = makeInput("Reservation number (e.g. R001)...");
        TextField guestNameField   = makeInput("Guest name...");
        TextField roomNumberField  = makeInput("Room number (e.g. 101)...");
        TextField durationField    = makeIntegerInput("Duration in days (e.g. 3)...");
        Label statusLabel          = new Label("");
        TextArea outputArea        = makeOutputArea();
        Button bookBtn             = makeButton("Create Booking", "#6a1b9a");

        bookBtn.setOnAction(e -> {
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
                if (duration <= 0) {
                    throw new IllegalArgumentException(
                            "Duration must be at least 1 day!");
                }
                if (duration > 365) {
                    throw new IllegalArgumentException(
                            "Duration cannot exceed 365 days!");
                }

                // ✅ SAVE TO DATABASE
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
        });

        // VIEW RECORDS
        TextArea recordsArea    = makeRecordsArea();
        Button viewBookingsBtn  = makeButton("View All Bookings", "#6a1b9a");

        viewBookingsBtn.setOnAction(e -> {
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
        });

        panel.getChildren().addAll(
                title,
                makeLabel("Reservation Number:"), centered(reservationField),
                makeLabel("Guest Name:"),          centered(guestNameField),
                makeLabel("Room Number:"),         centered(roomNumberField),
                makeLabel("Duration (days):"),     centered(durationField),
                centered(bookBtn),                 centered(statusLabel),
                centered(outputArea),
                new Separator(),
                makeTitle("📋 Booking Records"),
                centered(viewBookingsBtn),
                centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // PANEL: Invoice
    // ─────────────────────────────
    private VBox buildInvoicePanel() {

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(28));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #fff5f5;");

        Label title = makeTitle("💳 Invoice & Payment");

        TextField amountField    = makeDecimalInput("Charge amount (e.g. 150.0)...");
        ComboBox<String> paymentBox = makeComboBox("Select payment method...",
                "Credit Card", "Cash", "Check");
        TextField extraField  = makeInput(
                "Card holder name / Cash amount / Bank name...");
        Label statusLabel     = new Label("");
        TextArea outputArea   = makeOutputArea();
        Button payBtn         = makeButton("Process Payment", "#b71c1c");

        Invoice invoice = new Invoice();

        payBtn.setOnAction(e -> {
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
                    transaction = new CreditCardTransaction(
                            amount, extra, "00000");
                } else if (method.equals("Cash")) {
                    double cashGiven = Double.parseDouble(extra);
                    transaction = new CashTransaction(amount, cashGiven);
                } else {
                    transaction = new CheckTransaction(
                            amount, extra, "CHK001");
                }
                transaction.initiateTransaction();

                // ✅ SAVE TO DATABASE
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
                statusLabel.setText(
                        "❌ For Cash, enter a valid number in Details!");
            } catch (IllegalArgumentException ex) {
                statusLabel.setStyle("-fx-text-fill: #c62828;");
                statusLabel.setText("❌ " + ex.getMessage());
            }
        });

        // VIEW RECORDS
        TextArea recordsArea   = makeRecordsArea();
        Button viewInvoicesBtn = makeButton("View All Invoices", "#6a1b9a");

        viewInvoicesBtn.setOnAction(e -> {
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
        });

        panel.getChildren().addAll(
                title,
                makeLabel("Charge Amount ($):"), centered(amountField),
                makeLabel("Payment Method:"),     centered(paymentBox),
                makeLabel("Details:"),            centered(extraField),
                centered(payBtn),                 centered(statusLabel),
                centered(outputArea),
                new Separator(),
                makeTitle("📋 Invoice Records"),
                centered(viewInvoicesBtn),
                centered(recordsArea)
        );
        return panel;
    }

    // ─────────────────────────────
    // HELPER: reset nav buttons
    // ─────────────────────────────
    private void resetButtons(String style, Button... buttons) {
        for (Button btn : buttons) {
            btn.setStyle(style);
        }
    }
}