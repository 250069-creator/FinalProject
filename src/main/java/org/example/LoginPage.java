package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Login page shown when app first opens
// User must enter correct username and password
// Routes to correct dashboard based on role
public class LoginPage {

    private DatabaseManager db;
    private Stage stage;

    // constructor receives db and the main window
    public LoginPage(DatabaseManager db, Stage stage) {
        this.db    = db;
        this.stage = stage;
    }

    // ─────────────────────────────
    // Builds and shows the login page
    // ─────────────────────────────
    public void show() {

        // ── HEADER ──────────────────────────
        Label hotelLabel = new Label("🏨  GRAND HOTEL");
        hotelLabel.setStyle(
                "-fx-font-size: 30px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f;"
        );

        Label systemLabel = new Label("Management System");
        systemLabel.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: #7a6a5a;"
        );

        Label loginLabel = new Label("Please login to continue");
        loginLabel.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: #999999;"
        );

        VBox headerBox = new VBox(5, hotelLabel, systemLabel, loginLabel);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(30, 0, 20, 0));

        // ── LOGIN FORM ───────────────────────
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #4a3728;"
        );

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username...");
        usernameField.setMaxWidth(320);
        usernameField.setPrefWidth(320);
        usernameField.setStyle(
                "-fx-pref-height: 38px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #4a3728;"
        );

        // PasswordField hides typed characters with dots
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password...");
        passwordField.setMaxWidth(320);
        passwordField.setPrefWidth(320);
        passwordField.setStyle(
                "-fx-pref-height: 38px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-background-color: #fffde8; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-border-color: #c8b89a; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-text-fill: #2c2c2c;"
        );

        // status label for errors
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 12px;");

        // login button
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(320);
        loginBtn.setPrefWidth(320);
        loginBtn.setStyle(
                "-fx-background-color: #5a4a3a; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-cursor: hand;"
        );
        loginBtn.setOnMouseEntered(e -> loginBtn.setOpacity(0.85));
        loginBtn.setOnMouseExited(e -> loginBtn.setOpacity(1.0));

        // what happens when login button is clicked
        loginBtn.setOnAction(e -> handleLogin(
                usernameField, passwordField, statusLabel
        ));

        // also allow pressing Enter key to login
        passwordField.setOnAction(e -> handleLogin(
                usernameField, passwordField, statusLabel
        ));

        // hint text for default manager credentials
        Label hintLabel = new Label("Default Manager → username: manager   password: manager123");
        hintLabel.setStyle(
                "-fx-font-size: 10px; " +
                        "-fx-text-fill: #bbbbbb; " +
                        "-fx-padding: 10 0 0 0;"
        );

        // ── FORM CARD ────────────────────────
        VBox formCard = new VBox(10);
        formCard.setAlignment(Pos.CENTER_LEFT);
        formCard.setMaxWidth(350);
        formCard.setPadding(new Insets(30));
        formCard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);"
        );
        formCard.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginBtn,
                statusLabel,
                hintLabel
        );

        // ── FULL PAGE LAYOUT ─────────────────
        VBox fullPage = new VBox(0, headerBox, formCard);
        fullPage.setAlignment(Pos.TOP_CENTER);
        fullPage.setPadding(new Insets(20));
        fullPage.setStyle("-fx-background-color: #fdf3e3;");

        // show the login scene
        Scene loginScene = new Scene(fullPage, 500, 560);
        stage.setTitle("Grand Hotel — Login");
        stage.setScene(loginScene);
        stage.show();
    }

    // ─────────────────────────────
    // Handles login button click
    // ─────────────────────────────
    private void handleLogin(TextField usernameField,
                             PasswordField passwordField,
                             Label statusLabel) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // check empty fields
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Please enter username and password!");
            return;
        }

        // check credentials in database
        String role = db.checkLogin(username, password);

        if (role == null) {
            // wrong username or password
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Wrong username or password. Try again!");
            passwordField.clear(); // clear password field only
            return;
        }

        // login successful — route to correct dashboard
        if (role.equals("MANAGER")) {
            // manager goes to manager dashboard
            openManagerDashboard(username);

        } else if (role.equals("RECEPTIONIST")) {
            // receptionist goes to main hotel system
            openReceptionistDashboard(username);
        }
    }

    // ─────────────────────────────
    // Opens the Manager dashboard
    // ─────────────────────────────
    private void openManagerDashboard(String username) {
        ManagerDashboard managerDashboard = new ManagerDashboard(db, stage, username);
        managerDashboard.show();
    }

    // ─────────────────────────────
    // Opens the Receptionist dashboard (main hotel system)
    // ─────────────────────────────
    private void openReceptionistDashboard(String username) {
        ReceptionistDashboard receptionistDashboard =
                new ReceptionistDashboard(db, stage, username);
        receptionistDashboard.show();
    }
}