package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Manager dashboard
// Manager can ONLY create and delete receptionist accounts
// Manager cannot access hotel operations
public class ManagerDashboard {

    private DatabaseManager db;
    private Stage stage;
    private String managerUsername;

    public ManagerDashboard(DatabaseManager db, Stage stage, String managerUsername) {
        this.db              = db;
        this.stage           = stage;
        this.managerUsername = managerUsername;
    }

    // ─────────────────────────────
    // Builds and shows the manager dashboard
    // ─────────────────────────────
    public void show() {

        // ── HEADER ──────────────────────────
        Label titleLabel = new Label("🏨  GRAND HOTEL  —  Manager Panel");
        titleLabel.setStyle(
                "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f;"
        );

        Label welcomeLabel = new Label("Welcome, " + managerUsername + " 👋");
        welcomeLabel.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-text-fill: #7a6a5a;"
        );

        // logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: #c62828; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 6px 16px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        );
        logoutBtn.setOnAction(e -> logout());

        HBox headerRight = new HBox(10, welcomeLabel, logoutBtn);
        headerRight.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(titleLabel, headerRight);
        HBox.setHgrow(headerRight, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle(
                "-fx-background-color: #fdf3e3; " +
                        "-fx-border-color: #e8d5b7; " +
                        "-fx-border-width: 0 0 2 0;"
        );

        // ── CREATE RECEPTIONIST FORM ─────────
        Label createTitle = new Label("➕ Create Receptionist Account");
        createTitle.setStyle(
                "-fx-font-size: 17px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f; " +
                        "-fx-padding: 0 0 8 0;"
        );

        Label newUsernameLabel = UIHelper.makeLabel("New Username:");
        TextField newUsernameField = UIHelper.makeInput("Enter username for receptionist...");

        Label newPasswordLabel = UIHelper.makeLabel("New Password:");
        TextField newPasswordField = UIHelper.makeInput("Enter password for receptionist...");

        Label createStatus = new Label("");
        createStatus.setStyle("-fx-font-size: 12px;");

        Button createBtn = UIHelper.makeButton("Create Account", "#388e3c");

        createBtn.setOnAction(e -> handleCreateReceptionist(
                newUsernameField, newPasswordField, createStatus
        ));

        // ── VIEW RECEPTIONISTS ───────────────
        Label viewTitle = new Label("👥 All Receptionist Accounts");
        viewTitle.setStyle(
                "-fx-font-size: 17px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #3b2f2f; " +
                        "-fx-padding: 10 0 8 0;"
        );

        TextArea receptList = UIHelper.makeRecordsArea();
        Button viewBtn = UIHelper.makeButton("Refresh List", "#1565c0");
        viewBtn.setOnAction(e -> loadReceptionists(receptList));

        // ── DELETE RECEPTIONIST ──────────────
        Label deleteTitle = UIHelper.makeLabel("Delete Receptionist Account:");
        TextField deleteUsernameField = UIHelper.makeInput("Enter username to delete...");
        Label deleteStatus = new Label("");
        deleteStatus.setStyle("-fx-font-size: 12px;");

        Button deleteBtn = UIHelper.makeButton("Delete Account", "#c62828");
        deleteBtn.setOnAction(e -> handleDeleteReceptionist(
                deleteUsernameField, deleteStatus, receptList
        ));

        // ── FULL PANEL LAYOUT ────────────────
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(25));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #fff8f0;");

        panel.getChildren().addAll(
                createTitle,
                newUsernameLabel, UIHelper.centered(newUsernameField),
                newPasswordLabel, UIHelper.centered(newPasswordField),
                UIHelper.centered(createBtn),
                UIHelper.centered(createStatus),
                new Separator(),
                viewTitle,
                UIHelper.centered(viewBtn),
                UIHelper.centered(receptList),
                new Separator(),
                deleteTitle,
                UIHelper.centered(deleteUsernameField),
                UIHelper.centered(deleteBtn),
                UIHelper.centered(deleteStatus)
        );

        ScrollPane scrollPane = new ScrollPane(panel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(scrollPane);
        mainLayout.setStyle("-fx-background-color: #fff8f0;");

        Scene scene = new Scene(mainLayout, 680, 600);
        stage.setTitle("Grand Hotel — Manager Dashboard");
        stage.setScene(scene);

        // load receptionists list automatically
        loadReceptionists(receptList);
    }

    // ─────────────────────────────
    // Creates a new receptionist account
    // ─────────────────────────────
    private void handleCreateReceptionist(TextField usernameField,
                                          TextField passwordField,
                                          Label statusLabel) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // check empty fields
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Please fill in both username and password!");
            return;
        }

        // check minimum password length
        if (password.length() < 6) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Password must be at least 6 characters!");
            return;
        }

        // save to database
        boolean saved = db.saveReceptionist(username, password);

        if (saved) {
            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Account created for: " + username);
            usernameField.clear();
            passwordField.clear();
        } else {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Username already exists! Choose a different one.");
        }
    }

    // ─────────────────────────────
    // Deletes a receptionist account
    // ─────────────────────────────
    private void handleDeleteReceptionist(TextField deleteField,
                                          Label statusLabel,
                                          TextArea receptList) {
        String username = deleteField.getText().trim();

        if (username.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Enter the username to delete!");
            return;
        }

        // cannot delete yourself
        if (username.equals("manager")) {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Cannot delete the manager account!");
            return;
        }

        boolean deleted = db.deleteReceptionist(username);

        if (deleted) {
            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
            statusLabel.setText("✅ Account deleted: " + username);
            deleteField.clear();
            loadReceptionists(receptList); // refresh the list
        } else {
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            statusLabel.setText("❌ Username not found!");
        }
    }

    // ─────────────────────────────
    // Loads and shows all receptionists
    // ─────────────────────────────
    private void loadReceptionists(TextArea receptList) {
        var records = db.getAllReceptionists();

        if (records.isEmpty()) {
            receptList.setText("No receptionist accounts yet.\nCreate one above.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(records.get(i)).append("\n\n");
        }
        receptList.setText(sb.toString());
    }

    // ─────────────────────────────
    // Logs out and returns to login page
    // ─────────────────────────────
    private void logout() {
        LoginPage loginPage = new LoginPage(db, stage);
        loginPage.show();
    }
}
