package controller;

import Database.DatabaseUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users;
import util.ErrorHandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.*;


public class Login implements Initializable {
    public Label dateContainer;
    public TextField passWord;
    public TextField userName;
    public Button loginButton;
    public Label welcomeLabel;

    public static Users userHandoff = null;

    LocalDateTime nowDateTime = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    DateTimeFormatter dtfFrance = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    ObservableList<Users> users = DatabaseUsers.getUsers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(new Locale("fr"));
        LocalTime hour = LocalTime.of(1, 0);

        for(int i = 0; i < 24; i++){
            System.out.println(hour);
           hour = hour.plusHours(1);
        }

        ResourceBundle rb = ResourceBundle.getBundle("Nat_fr", Locale.getDefault());
        ZoneId zoneId = ZoneId.systemDefault();
        dateContainer.setText(String.valueOf(zoneId));
        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                welcomeLabel.setText(rb.getString("Please") + " " + rb.getString("Login"));
                loginButton.setText(rb.getString("Login"));
                userName.setPromptText(rb.getString("Username"));
                passWord.setPromptText(rb.getString("Password"));
            }
        } catch (Exception e) {
            System.out.println("Language pack not found");
        }


    }

    public void writer(boolean bool) throws IOException {

        File loginAttempts = new File("Login Attempts.txt");

        if(!loginAttempts.exists()){
            loginAttempts.createNewFile();
        }

        FileWriter fw = new FileWriter(loginAttempts, true);

        PrintWriter pw = new PrintWriter(fw);
        if(bool == true) {
            pw.println("Successful Login " + nowDateTime.getDayOfWeek() + " " + nowDateTime.format(dtf));

        }
        else{
            pw.println("Failed Attempt " + nowDateTime.getDayOfWeek() + " " + nowDateTime.format(dtf));
        }

        pw.flush();
        pw.close();
    }

    public static Users getUserHandoff(){
        return userHandoff;
    }



    public void onLogin(ActionEvent actionEvent) throws IOException {
        for(Users u : users){
            if(userName.getText().equals(u.getUserName())){
                if(passWord.getText().equals(u.getPassWord())){
                    u.setActive(true);
                    userHandoff = u;
                    String name = u.getUserName();
                    writer(true);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1100, 550);
                    stage.setTitle("Customers");
                    stage.setScene(scene);
                    stage.show();
                    ErrorHandling.displayInformation("Welcome " + name);
                    break;
                }
                else{
                    writer(false);
                    ErrorHandling.displayError("Incorrect Password");
                    break;
                }
            }
            else{
                writer(false);
                ErrorHandling.displayError("Username not found");
               break;
            }
        }
    }
}