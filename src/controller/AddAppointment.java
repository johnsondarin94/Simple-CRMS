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
    public TextField location;


    public void onAdd(ActionEvent actionEvent) {
        try{
            String activeUser = Login.getUserHandoff().getUserName();
            String title = appointmentTitle.getText();
            String description = String.valueOf(appointmentDescription.getText());
            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
            String type = appointmentType.getText();
            String loca = location.getText();

            Customers selectedCustomer = (Customers) customerIDComboBox.getSelectionModel().getSelectedItem();
            int customerId = selectedCustomer.getCustomerId();

            Users selectedUser = (Users) userIDComboBox.getSelectionModel().getSelectedItem();
            int userID = selectedUser.getUserID();

            LocalDate startDate = appointmentStartDate.getValue();
            LocalTime startTime = appointmentStartTime.getSelectionModel().getSelectedItem();
            LocalTime endTime = appointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

            if(checkForAptOverlap(customerId, startDateTime, endDateTime) && checkBusinessHours(startDateTime, endDateTime) && checkInverseHours(startDateTime, endDateTime)) {
                DatabaseAppointments.addAppointment(title, description, loca, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID);
            }

        } catch (NullPointerException e) {
            ErrorHandling.displayError("Please ensure all fields are populated.");
        }
    }
    public boolean checkInverseHours(LocalDateTime sdt, LocalDateTime edt){
        if(sdt.isAfter(edt) || edt.isBefore(sdt) || sdt.isEqual(edt)){
            ErrorHandling.displayError("Please check start and date times for chronological order.");
            return false;
        }
        return true;
    }

    public boolean checkForAptOverlap(int customerId, LocalDateTime sdt, LocalDateTime edt) {
        ObservableList<Appointments> overlapList = DatabaseAppointments.getAssociatedAppointments(customerId);

        for (Appointments oLap : overlapList) {
            LocalDateTime start = oLap.getStartDateTime();
            LocalDateTime end = oLap.getEndDateTime();

            if ((sdt.isAfter(start) || sdt.isEqual(start)) && sdt.isBefore(end)) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                return false;
            }
            if (edt.isAfter(start) && (edt.isBefore(end) || edt.isEqual(end))) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                return false;
            }
            if ((sdt.isBefore(start) || sdt.isEqual(start) && (edt.isAfter(end) || edt.isEqual(end)))) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
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

        ZonedDateTime startDate = sdt.atZone(ZoneId.systemDefault());
        ZonedDateTime endDate = edt.atZone(ZoneId.systemDefault());

        if (startDate.isAfter(businessEnd) || startDate.isBefore(businessStart)){
            ErrorHandling.displayError("Appointment does not fall withing business hours. 8am - 10pm EST");
            return false;
        }

        if (endDate.isAfter(businessEnd) || endDate.isBefore(businessStart)){
            System.out.println("Statement 2 was reached");
            return false;
        }
        else{
            System.out.println("Statement 3 was reached");
            return true;
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
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
        ObservableList<LocalTime> hours = Appointments.getHours();
        appointmentStartTime.setItems(hours);
        appointmentEndTime.setItems(hours);

    }
}
