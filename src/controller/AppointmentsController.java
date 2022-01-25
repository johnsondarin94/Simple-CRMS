package controller;

import Database.DatabaseAppointments;
import Database.DatabaseCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import util.ErrorHandling;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
    public Button deleteButton;
    public RadioButton sbmRB;
    public RadioButton sbwRB;
    public RadioButton noFilterRB;

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
            ErrorHandling.displayError("Please select an Appointment to Update");
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
        Scene scene = new Scene(root, 1100, 550);
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

    public void onDelete(ActionEvent actionEvent) {
        try{
            appointmentHandOff = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();
            int id = getAppointmentHandOff().getAppointment_ID();
            DatabaseAppointments.deleteAppointment(id);
            appointmentsTable.getSelectionModel().clearSelection();
            ErrorHandling.displayInformation("Appointment Deleted.");
        } catch (Exception e) {
            ErrorHandling.displayError("Please select an Appointment to delete");
        }

    }

    //public void populateTable

    public void onSortMonth(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();;
        LocalDateTime datePlusMonth = LocalDateTime.now().plusMonths(1);

            for (Appointments a : appointments) {
                if (a.getStartDateTime().isBefore(datePlusMonth) || a.getStartDateTime().isEqual(datePlusMonth)) {
                     filteredAppointments.add(a);
                }
            }
            appointmentsTable.setItems(filteredAppointments);
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

    public void onSortWeek(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
        LocalDateTime datePlusWeek = LocalDateTime.now().plusWeeks(1);

        for(Appointments a: appointments){
            if(a.getStartDateTime().isBefore(datePlusWeek) || a.getStartDateTime().isEqual(datePlusWeek)){
                filteredAppointments.add(a);

                System.out.println("WE MADE IT HERE");
            }
        }
        appointmentsTable.setItems(filteredAppointments);
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

    public void onNoFilter(ActionEvent actionEvent) {
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
