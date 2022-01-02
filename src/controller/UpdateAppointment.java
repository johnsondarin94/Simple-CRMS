package controller;

import Database.DatabaseAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
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
    public TextField appointmentContact;
    public ComboBox appointmentStartTime;
    public ComboBox appointmentEndTime;
    public ComboBox appointmentUserID;
    public Button updateButton;

    private Appointments appointmentToModify = null;

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentToModify = AppointmentsController.getAppointmentHandOff();

        appointmentID.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        appointmentTitle.setText(String.valueOf(appointmentToModify.getTitle()));
        appointmentDescription.setText(String.valueOf(appointmentToModify.getDescription()));
        appointmentType.setText(String.valueOf(appointmentToModify.getType()));
        appointmentContact.setText(String.valueOf(appointmentToModify.getContact()));

    }

    public void onUpdate(ActionEvent actionEvent) {

        int id = Integer.parseInt(appointmentID.getText());
        String title = appointmentTitle.getText();
        String description = appointmentDescription.getText();
        String type = appointmentType.getText();
        String contact = appointmentContact.getText();

        DatabaseAppointments.updateAppointment(id, title, description, type, contact);
    }
}
