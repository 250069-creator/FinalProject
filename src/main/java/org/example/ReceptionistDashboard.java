package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Receptionist dashboard
// This is the full hotel management system
// Only accessible to logged-in receptionists
public class ReceptionistDashboard {

    private DatabaseManager db;
    private Stage stage;
    private String receptionistUsername;

    public ReceptionistDashboard(DatabaseManager db, Stage stage, String receptionistUsername) {
        this.db                    = db;
        this.stage                 = stage;
        this.receptionistUsername  = receptionistUsername;
    }

    // ─────────────────────────────
    // Builds and shows the receptionist dashboard
    // ─────────────────────────────
    public void show() {

        // ── HEADER ──────────────────────────
        Label titleLabel = new Label("🏨  GRAND HOTEL");
        titleLabel.setStyle(
                "-fx-font-size: 22px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f;"
        );

        Label subtitleLabel = new Label("Management System");
        subtitleLabel.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: #7a6a5a;"
        );

        VBox titleStack = new VBox(2, titleLabel, subtitleLabel);

        Label welcomeLabel = new Label("👤 " + receptionistUsername);
        welcomeLabel.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: #5a4a3a; " +
                        "-fx-font-weight: bold;"
        );

        // logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: #c62828; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 11px; " +
                        "-fx-padding: 5px 14px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        );
        logoutBtn.setOnAction(e -> logout());

        VBox rightBox = new VBox(4, welcomeLabel, logoutBtn);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(titleStack, rightBox);
        HBox.setHgrow(rightBox, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(14, 20, 14, 20));
        header.setStyle(
                "-fx-background-color: #fdf3e3; " +
                        "-fx-border-color: #e8d5b7; " +
                        "-fx-border-width: 0 0 2 0;"
        );

        // ── NAV BUTTONS ─────────────────────
        Button notifBtn   = new Button("🔔 Notifications");
        Button roomBtn    = new Button("🛏 Rooms");
        Button guestBtn   = new Button("👤 Guests");
        Button bookingBtn = new Button("📋 Bookings");
        Button invoiceBtn = new Button("💳 Invoice");

        notifBtn.setStyle(UIHelper.NAV_DEFAULT);
        roomBtn.setStyle(UIHelper.NAV_DEFAULT);
        guestBtn.setStyle(UIHelper.NAV_DEFAULT);
        bookingBtn.setStyle(UIHelper.NAV_DEFAULT);
        invoiceBtn.setStyle(UIHelper.NAV_DEFAULT);

        HBox navBar = new HBox(8, notifBtn, roomBtn,
                guestBtn, bookingBtn, invoiceBtn);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #f5ede0;");

        // ── BUILD ALL PANELS ─────────────────
        VBox notifPanel   = new NotificationPanel(db).buildPanel();
        VBox roomPanel    = new RoomPanel(db).buildPanel();
        VBox guestPanel   = new GuestPanel(db).buildPanel();
        VBox bookingPanel = new BookingPanel(db).buildPanel();
        VBox invoicePanel = new InvoicePanel(db).buildPanel();

        ScrollPane scrollPane = new ScrollPane(notifPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background: transparent;"
        );

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(new VBox(header, navBar));
        mainLayout.setCenter(scrollPane);
        mainLayout.setStyle("-fx-background-color: #fffaf4;");

        // ── NAV BUTTON ACTIONS ───────────────
        notifBtn.setOnAction(e -> {
            scrollPane.setContent(notifPanel);
            resetButtons(notifBtn, roomBtn, guestBtn, bookingBtn, invoiceBtn);
            notifBtn.setStyle(UIHelper.NAV_DEFAULT
                    .replace("#e8e0d5", "#a8d5a2")
                    .replace("#5a4a3a", "#1b5e20") + "-fx-text-fill: #1b5e20;");
        });

        roomBtn.setOnAction(e -> {
            scrollPane.setContent(roomPanel);
            resetButtons(notifBtn, roomBtn, guestBtn, bookingBtn, invoiceBtn);
            roomBtn.setStyle(UIHelper.NAV_DEFAULT
                    .replace("#e8e0d5", "#a3c4f3")
                    .replace("#5a4a3a", "#0d3b8e") + "-fx-text-fill: #0d3b8e;");
        });

        guestBtn.setOnAction(e -> {
            scrollPane.setContent(guestPanel);
            resetButtons(notifBtn, roomBtn, guestBtn, bookingBtn, invoiceBtn);
            guestBtn.setStyle(UIHelper.NAV_DEFAULT
                    .replace("#e8e0d5", "#ffd8a8")
                    .replace("#5a4a3a", "#7a3e00") + "-fx-text-fill: #7a3e00;");
        });

        bookingBtn.setOnAction(e -> {
            scrollPane.setContent(bookingPanel);
            resetButtons(notifBtn, roomBtn, guestBtn, bookingBtn, invoiceBtn);
            bookingBtn.setStyle(UIHelper.NAV_DEFAULT
                    .replace("#e8e0d5", "#d4b8f0")
                    .replace("#5a4a3a", "#4a148c") + "-fx-text-fill: #4a148c;");
        });

        invoiceBtn.setOnAction(e -> {
            scrollPane.setContent(invoicePanel);
            resetButtons(notifBtn, roomBtn, guestBtn, bookingBtn, invoiceBtn);
            invoiceBtn.setStyle(UIHelper.NAV_DEFAULT
                    .replace("#e8e0d5", "#f5b8b8")
                    .replace("#5a4a3a", "#7f0000") + "-fx-text-fill: #7f0000;");
        });

        Scene scene = new Scene(mainLayout, 680, 560);
        stage.setTitle("Grand Hotel — " + receptionistUsername);
        stage.setScene(scene);
    }

    // resets all nav buttons to default style
    private void resetButtons(Button... buttons) {
        for (Button btn : buttons) {
            btn.setStyle(UIHelper.NAV_DEFAULT);
        }
    }

    // logs out and returns to login page
    private void logout() {
        LoginPage loginPage = new LoginPage(db, stage);
        loginPage.show();
    }
}