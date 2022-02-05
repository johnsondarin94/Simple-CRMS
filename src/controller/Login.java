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
import util.Navigation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.time.*;

/**Login Controller for Login*/
public class Login implements Initializable {
    public Label dateContainer;
    public TextField passWord;
    public TextField userName;
    public Button loginButton;
    public Label welcomeLabel;

    public static Users userHandoff = null;

    LocalDateTime nowDateTime = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    ObservableList<Users> users = DatabaseUsers.getUsers();

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**Writer method writes to a text file each time a login attempt is made. Takes in a boolean to determine whether
     * the login attempt was successful or not.
     * @param bool Boolean lets the method know if the login was a success or not and how to handle it.*/
    public void writer(boolean bool) throws IOException {

        File loginAttempts = new File("login_activity.txt");

        if(!loginAttempts.exists()){
            loginAttempts.createNewFile();
        }

        FileWriter fw = new FileWriter(loginAttempts, true);

        PrintWriter pw = new PrintWriter(fw);
        if(bool) {
            pw.println("Successful Login " + nowDateTime.getDayOfWeek() + " " + nowDateTime.format(dtf));

        }
        else{
            pw.println("Failed Attempt " + nowDateTime.getDayOfWeek() + " " + nowDateTime.format(dtf));
        }

        pw.flush();
        pw.close();
    }

    /**UserHandOff method returns the active user. Used throughout the application.
     * @return Returns a Users object*/
    public static Users getUserHandoff(){
        return userHandoff;
    }

    /**Method handles logging in, validating credentials and sending a boolean to the writer depending on outcome.
     * On successful login, method grabs User information and stores it for later use.
     * @param actionEvent Login Action Event*/
    public void onLogin(ActionEvent actionEvent) throws IOException {
        for(Users u : users){
            if(userName.getText().equals(u.getUserName())){
                if(passWord.getText().equals(u.getPassWord())){
                    u.setActive(true);
                    userHandoff = u;
                    String name = u.getUserName();
                    writer(true);
                    navigate.navigate(actionEvent,"/view/Customers.fxml", "Customers", 1100, 550);
                    CustomerController.checkForAppointments();
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

    /**Initialize method for the Login controller. Method grabs computer locale and changes login screen language from
     * either French or English depending on location. Localization is done using a Resource Bundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(new Locale("fr"));
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
}