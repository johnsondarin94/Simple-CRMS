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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable{
    public Button addButton;
    public Button cancelButton;
    public DatePicker appointmentStartDate;
    public TextField appointmentId;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public TextField appointmentContact;
    public ComboBox<LocalTime> appointmentStartTime;
    public ComboBox<LocalTime>appointmentEndTime;
    public ComboBox userIDComboBox;
    public ComboBox customerIDComboBox;
    public ComboBox<Contacts> contactComboBox;


    public void onAdd(ActionEvent actionEvent) {
        try{
            String activeUser = Login.getUserHandoff().getUserName();
            String title = appointmentTitle.getText();
            String description = appointmentDescription.getText();
            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
            String type = appointmentType.getText();

            Customers selectedCustomer = (Customers) customerIDComboBox.getSelectionModel().getSelectedItem();
            int customerId = selectedCustomer.getCustomerId();

            Users selectedUser = (Users) userIDComboBox.getSelectionModel().getSelectedItem();
            int userID = selectedUser.getUserID();

            LocalDate startDate = appointmentStartDate.getValue();
            LocalTime startTime = appointmentStartTime.getSelectionModel().getSelectedItem();
            LocalTime endTime = appointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

            if(checkForAptOverlap(customerId, startDateTime, endDateTime) && checkBusinessHours(startDateTime, endDateTime)) {
                DatabaseAppointments.addAppointment(title, description, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID);
            }
            else{
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
            }
        } catch (NullPointerException e) {
            ErrorHandling.displayError("Please ensure all fields are populated.");
        }
    }

    public boolean checkForAptOverlap(int customerId, LocalDateTime sdt, LocalDateTime edt) {
        ObservableList<Appointments> overlapList = DatabaseAppointments.getAssociatedAppointments(customerId);

        for (Appointments oLap : overlapList) {
            LocalDateTime start = oLap.getStartDateTime();
            LocalDateTime end = oLap.getEndDateTime();

            if ((sdt.isAfter(start) || sdt.isEqual(start)) && sdt.isBefore(end)) {
                return false;
            }
            if (edt.isAfter(start) && (edt.isBefore(end) || edt.isEqual(end))) {
                return false;
            }
            if ((sdt.isBefore(start) || sdt.isEqual(start) && (edt.isAfter(end) || edt.isEqual(end)))) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean checkBusinessHours(LocalDateTime sdt, LocalDateTime edt){
        ZonedDateTime businessStart = ZonedDateTime.of(sdt.toLocalDate(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime businessEnd = ZonedDateTime.of(sdt.toLocalDate(), LocalTime.of(22,0), ZoneId.of("America/New_York"));
        return true;
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustomerID(ActionEvent actionEvent) {
    }

    public static ObservableList<LocalTime> getHours(){
        ObservableList<LocalTime> hours = FXCollections.observableArrayList();
        LocalTime hour = LocalTime.of(0, 0);
        System.out.println(hour);
        for(int i = 0; i < 24; i++){
            hour = hour.plusHours(1);

            hours.add(hour);
        }
        return hours;
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
        ObservableList<LocalTime> hours = getHours();
        appointmentStartTime.setItems(hours);
        appointmentEndTime.setItems(hours);

    }
}
