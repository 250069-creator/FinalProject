
package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    DatabaseManager db = new DatabaseManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        db.connect();
        db.createTables();
        db.createUsersTable();

        stage.setOnCloseRequest(e -> db.disconnect());

        LoginPage loginPage = new LoginPage(db, stage);
        loginPage.show();
    }
}
