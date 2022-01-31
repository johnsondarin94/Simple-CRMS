package controller;

import Database.DatabaseAppointments;
import Database.DatabaseCustomers;
import Database.DatabaseUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import util.ErrorHandling;
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable {
    public Button cancelButton;
    public ComboBox appointmentCustomerID;
    public DatePicker appointmentStartDate;
    public TextField appointmentID;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public ComboBox<LocalTime> appointmentStartTime;
    public ComboBox<LocalTime> appointmentEndTime;
    public ComboBox appointmentUserID;
    public Button updateButton;
    public ComboBox<Contacts> updateAppointmentContact;
    public TextField updateLocation;

    private Appointments appointmentToModify = null;

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1100, 550);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> customers = DatabaseCustomers.getAllCustomers();
        appointmentCustomerID.setItems(customers);
        ObservableList<Users> users = DatabaseUsers.getUsers();
        appointmentUserID.setItems(users);
        ObservableList<Contacts> contacts = DatabaseAppointments.getAllContacts();
        updateAppointmentContact.setItems(contacts);
        ObservableList<LocalTime> hours = Appointments.getHours();
        appointmentStartTime.setItems(hours);
        appointmentEndTime.setItems(hours);

        appointmentToModify = AppointmentsController.getAppointmentHandOff();

        appointmentID.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        appointmentTitle.setText(String.valueOf(appointmentToModify.getTitle()));
        appointmentDescription.setText(String.valueOf(appointmentToModify.getDescription()));
        appointmentType.setText(String.valueOf(appointmentToModify.getType()));
        updateLocation.setText(String.valueOf(appointmentToModify.getLocation()));
        appointmentStartDate.setValue(appointmentToModify.getStartDateTime().toLocalDate());
        appointmentStartTime.setValue(appointmentToModify.getStartDateTime().toLocalTime());
        appointmentEndTime.setValue(appointmentToModify.getEndDateTime().toLocalTime());
        appointmentCustomerID.setValue(DatabaseCustomers.getUpdateCustomer(appointmentToModify.getCustomerId()));
        appointmentUserID.setValue(DatabaseUsers.getSpecificUser(appointmentToModify.getUserId()));
        updateAppointmentContact.setValue(appointmentToModify.getContact());
    }

    public void onUpdate(ActionEvent actionEvent) throws IOException {
        String activeUser = Login.getUserHandoff().getUserName();
        int id = Integer.parseInt(appointmentID.getText());
        String title = appointmentTitle.getText();
        String description = String.valueOf(appointmentDescription.getText());
        String location = updateLocation.getText();
        String type = appointmentType.getText();
        int contactID = updateAppointmentContact.getSelectionModel().getSelectedItem().getContactID();

        Customers selectedCustomer = (Customers) appointmentCustomerID.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustomerId();

        Users selectedUser = (Users) appointmentUserID.getSelectionModel().getSelectedItem();
        int userID = selectedUser.getUserID();

        LocalDate startDate = appointmentStartDate.getValue();
        LocalTime startTime = appointmentStartTime.getSelectionModel().getSelectedItem();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalTime endTime = appointmentEndTime.getSelectionModel().getSelectedItem();
        LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

        DatabaseAppointments.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID);
        ErrorHandling.displayInformation("Appointment successfully updated");
        navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1000, 550);
    }
}
