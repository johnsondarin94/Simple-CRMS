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
    public DatePicker appointmentEndDate;
    public TextField appointmentID;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public ComboBox<LocalTime> appointmentStartTime;
    public ComboBox<LocalTime> appointmentEndTime;
    public ComboBox appointmentUserID;
    public Button updateButton;
    public ComboBox<Contacts> updateAppointmentContact;

    private Appointments appointmentToModify = null;

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> customers = DatabaseCustomers.getAllCustomers();
        appointmentCustomerID.setItems(customers);
        ObservableList<Users> users = DatabaseUsers.getUsers();
        appointmentUserID.setItems(users);
        ObservableList<Contacts> contacts = DatabaseAppointments.getAllContacts();
        updateAppointmentContact.setItems(contacts);
        ObservableList<LocalTime> hours = getHours();
        appointmentStartTime.setItems(hours);
        appointmentEndTime.setItems(hours);

        appointmentToModify = AppointmentsController.getAppointmentHandOff();

        appointmentID.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        appointmentTitle.setText(String.valueOf(appointmentToModify.getTitle()));
        appointmentDescription.setText(String.valueOf(appointmentToModify.getDescription()));
        appointmentType.setText(String.valueOf(appointmentToModify.getType()));
        appointmentStartDate.setValue(appointmentToModify.getStartDateTime().toLocalDate());
        appointmentStartTime.setValue(appointmentToModify.getStartDateTime().toLocalTime());
        appointmentEndDate.setValue(appointmentToModify.getEndDateTime().toLocalDate());
        appointmentEndTime.setValue(appointmentToModify.getEndDateTime().toLocalTime());
        appointmentCustomerID.setValue(appointmentToModify.getCustomerId());
        appointmentUserID.setValue(appointmentToModify.getUserId());
        updateAppointmentContact.setValue(appointmentToModify.getContact());


    }

    public void onUpdate(ActionEvent actionEvent) {
        String activeUser = Login.getUserHandoff().getUserName();
        int id = Integer.parseInt(appointmentID.getText());
        String title = appointmentTitle.getText();
        String description = appointmentDescription.getText();
        String type = appointmentType.getText();
        int contactID = updateAppointmentContact.getSelectionModel().getSelectedItem().getContactID();

        Customers selectedCustomer = (Customers) appointmentCustomerID.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustomerId();

        Users selectedUser = (Users) appointmentUserID.getSelectionModel().getSelectedItem();
        int userID = selectedUser.getUserID();

        LocalDate startDate = appointmentStartDate.getValue();
        LocalTime startTime = appointmentStartTime.getSelectionModel().getSelectedItem();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = appointmentEndDate.getValue();
        LocalTime endTime = appointmentEndTime.getSelectionModel().getSelectedItem();

        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        DatabaseAppointments.updateAppointment(id, title, description, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID);
    }
}
