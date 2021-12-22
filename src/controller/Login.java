package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users;
import util.ReaderWriter;

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

    LocalDateTime nowDateTime = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    DateTimeFormatter dtfFrance = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

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
        //System.out.println("We are getting here");
        pw.flush();
        pw.close();
    }

    //public Alert displayAlert(Alert)

   /* public boolean validator() {
        boolean isValid = false;
        if (userName.getText().equals("test") && passWord.getText().equals("test")) {
            isValid = true;
        }
        else{
            isValid = false;
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
            Optional<ButtonType> result = alert1.showAndWait();
        }
        return isValid;
    }
*/
    public void onLogin(ActionEvent actionEvent) throws IOException {
        Users test = new Users(1, "test", "test");
        Users bobPeterson = new Users(2, "bob", "peterson");
        if (userName.getText().equals(test.getUserName()) && passWord.getText().equals(test.getPassWord())) {
            writer(true);
            Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 750, 550);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();

            LocalDateTime nowDateTime = LocalDateTime.now();
            System.out.println(nowDateTime);
            dateContainer.setText(String.valueOf(nowDateTime));
        }
        else{
            writer(false);
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
            Optional<ButtonType> result = alert1.showAndWait();
        }
    }
}