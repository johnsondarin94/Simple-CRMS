package controller;

import Database.DatabaseAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddAppointment {
    public Button addButton;
    public Button cancelButton;
    public ComboBox appointmentCustomerId;
    public DatePicker appointmentStartDate;
    public DatePicker appointmentEndDate;
    public TextField appointmentId;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public TextField appointmentContact;
    public ComboBox appointmentStartTime;
    public ComboBox appointmentEndTime;

    public void onAdd(ActionEvent actionEvent) {
        String title = appointmentTitle.getText();
        String description = appointmentDescription.getText();
        String contact = appointmentContact.getText();
        String type = appointmentType.getText();
        int customerId = 5; // FIX ME RETURN CUSTOMER ID
        int userId = 6; // FIX ME RETURN USER ID

        DatabaseAppointments.addAppointment(3, title, description, contact, type, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),customerId, userId);
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
