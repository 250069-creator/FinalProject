package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {

    private DatabaseManager db;
    private Stage stage;

    public LoginPage(DatabaseManager db, Stage stage) {
        this.db    = db;
        this.stage = stage;
    }

    public void show() {

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

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 12px;");

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

        loginBtn.setOnAction(e -> handleLogin(
                usernameField, passwordField, statusLabel
        ));

        passwordField.setOnAction(e -> handleLogin(
                usernameField, passwordField, statusLabel
        ));

        Label hintLabel = new Label("Default Manager → username: manager   password: manager123");
        hintLabel.setStyle(
                "-fx-font-size: 10px; " +
                        "-fx-text-fill: #bbbbbb; " +
                        "-fx-padding: 10 0 0 0;"
        );

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

        VBox fullPage = new VBox(0, headerBox, formCard);
        fullPage.setAlignment(Pos.TOP_CENTER);
        fullPage.setPadding(new Insets(20));
        fullPage.setStyle("-fx-background-color: #fdf3e3;");

        Scene loginScene = new Scene(fullPage, 500, 560);
        stage.setTitle("Grand Hotel — Login");
        stage.setScene(loginScene);
        stage.show();
    }

    private void handleLogin(TextField usernameField,
                             PasswordField passwordField,
                             Label statusLabel) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Please enter username and password!");
            return;
        }

        String role = db.checkLogin(username, password);

        if (role == null) {

            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Wrong username or password. Try again!");
            passwordField.clear();
            return;
        }


        if (role.equals("MANAGER")) {

            openManagerDashboard(username);

        } else if (role.equals("RECEPTIONIST")) {

            openReceptionistDashboard(username);
        }
    }

    private void openManagerDashboard(String username) {
        ManagerDashboard managerDashboard = new ManagerDashboard(db, stage, username);
        managerDashboard.show();
    }

    private void openReceptionistDashboard(String username) {
        ReceptionistDashboard receptionistDashboard =
                new ReceptionistDashboard(db, stage, username);
        receptionistDashboard.show();
    }
}