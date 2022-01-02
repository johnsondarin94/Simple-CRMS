package controller;

import Database.DatabaseAppointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    public Button updateButton;
    public Button addButton;
    public Button cancelButton;
    public TableColumn appointmentID;
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn startDateandTime;
    public TableColumn endDateAndTime;
    public TableColumn customerID;
    public TableColumn userID;
    public TableView appointmentsTable;

    private static Appointments appointmentHandOff = null;

    public void onUpdate(ActionEvent actionEvent) throws IOException {

        try {
            appointmentHandOff = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 450, 500);
            stage.setTitle("Update Appointments");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Please select an appointment to update FIX ME");
        }
    }

    public static Appointments getAppointmentHandOff(){
        return appointmentHandOff;
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 500);
        stage.setTitle("Add Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        for(Appointments A : appointments){
            appointmentsTable.setItems(appointments);
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateandTime.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateAndTime.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        }
    }
}
