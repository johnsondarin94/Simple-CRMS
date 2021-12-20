package main;

import Database.DatabaseConnection;
import Database.DatabaseCustomers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 275, 350));
        stage.show();
    }

    public static void main(String[] args){
        DatabaseConnection.openConnection();
        DatabaseCustomers.getAllCustomers();
        launch(args);
        DatabaseConnection.closeConnection();
    }
}