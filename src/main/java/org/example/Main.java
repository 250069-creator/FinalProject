
package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

// Main class ONLY launches the app
// Shows login page first
// Everything else handled by other classes
public class Main extends Application {

    // one shared database manager for the whole app
    DatabaseManager db = new DatabaseManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // connect to database and create all tables
        db.connect();
        db.createTables();
        db.createUsersTable(); // creates users table + default manager account

        // close database when app closes
        stage.setOnCloseRequest(e -> db.disconnect());

        // show login page first — user must login
        LoginPage loginPage = new LoginPage(db, stage);
        loginPage.show();
    }
}
