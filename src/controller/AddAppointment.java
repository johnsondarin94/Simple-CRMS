package controller;

import Database.DatabaseAppointments;
import Database.DatabaseCustomers;
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
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable{
    public Button addButton;
    public Button cancelButton;
    public DatePicker appointmentStartDate;
    public DatePicker appointmentEndDate;
    public TextField appointmentId;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public TextField appointmentContact;
    public ComboBox appointmentStartTime;
    public ComboBox appointmentEndTime;
    public ComboBox userIDComboBox;
    public ComboBox customerIDComboBox;
    public ComboBox<Contacts> contactComboBox;

    public void onAdd(ActionEvent actionEvent) {
        String activeUser = Login.getUserHandoff().getUserName();
        String title = appointmentTitle.getText();
        String description = appointmentDescription.getText();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        String type = appointmentType.getText();


        Customers selectedCustomer = (Customers) customerIDComboBox.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustomerId();

        Users selectedUser = (Users) userIDComboBox.getSelectionModel().getSelectedItem();
        int userID = selectedUser.getUserID();

        DatabaseAppointments.addAppointment(title, description, type, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), activeUser, customerId, userID, contactID);
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustomerID(ActionEvent actionEvent) {
    }

    public void onStartTime(ActionEvent actionEvent) {
    }

    public void onEndTime(ActionEvent actionEvent) {
    }

    public void onUserID(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> customers = DatabaseCustomers.getAllCustomers();
        customerIDComboBox.setItems(customers);
        ObservableList<Users> users = DatabaseUsers.getUsers();
        userIDComboBox.setItems(users);
        ObservableList<Contacts> contacts = DatabaseAppointments.getAllContacts();
        contactComboBox.setItems(contacts);
    }
}
