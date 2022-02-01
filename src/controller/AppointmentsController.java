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
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**Controller Class for Appointments page. Displays a complete or filtered (by month or week) list of all appointments.
 * Handles navigation to Add or Update Appointments as well as the deletion of an appointment.*/
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

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**Method grabs selection appointment or displays error if there are no selected appointments. Also sends user to
     * the Update Appointments Page. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for the Update button*/
    public void onUpdate(ActionEvent actionEvent) throws IOException {

        try {
            appointmentHandOff = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();

            navigate.navigate(actionEvent, "/view/UpdateAppointment.fxml", "Update Appointments", 450, 500);

        } catch (IOException e) {
            ErrorHandling.displayError("Please select an Appointment to Update");
        }
    }

    /**Method returns an Appointment. Used to pass to Update Appointments page and deleting an appointment.
     * @return Return Appointment*/
    public static Appointments getAppointmentHandOff(){
        return appointmentHandOff;
    }

    /**Method navigates User to Add Appointments Page. LAMBDA 1 IS USED HERE
     * @param actionEvent Action Event for Add Button*/
    public void onAdd(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/AddAppointment.fxml", "Add Appointments", 450, 550);
    }

    /**Method navigates user to Customers Page. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for Cancel Button*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);
    }

    /**Method Handles deleting an appointment. Must have appointment selected. Displays an alert with ID and Type.
     * LAMBDA 1 IS USED HERE
     * @param actionEvent Action Event for Delete Button*/
    public void onDelete(ActionEvent actionEvent) {
        try{
            appointmentHandOff = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();
            int id = getAppointmentHandOff().getAppointment_ID();
            String type = getAppointmentHandOff().getType();

            if(ErrorHandling.displayConfirmation("Are you sure you want to delete appointment?")) {
                DatabaseAppointments.deleteAppointment(id);
                ErrorHandling.displayInformation("Appointment Deleted.\n" + "Appointment ID: " + id + " Appointment Type: " + type);
                appointmentsTable.getSelectionModel().clearSelection();
                navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1000, 550);
            }

        } catch (Exception e) {
            ErrorHandling.displayError("Please select an Appointment to delete");
        }

    }

    /**Method Refreshes tableview when changes are made. Calls database and generates a fresh list of appointments.
     * @param appointmentsList ObservableList of refreshed appointments. */
    public void refreshAppointments(ObservableList<Appointments> appointmentsList){
        appointmentsTable.setItems(appointmentsList);
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

    /**@Method refreshes appointments tableview with a list of appointments only occurring within a months time.
     * @param actionEvent  Action Event for Sort by Month Radio Button*/
    public void onSortMonth(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();;
        LocalDateTime datePlusMonth = LocalDateTime.now().plusMonths(1);

            for (Appointments a : appointments) {
                if (a.getStartDateTime().isBefore(datePlusMonth) || a.getStartDateTime().isEqual(datePlusMonth)) {
                     filteredAppointments.add(a);
                }
            }
            refreshAppointments(filteredAppointments);
        }

        /**Method refreshed appointments tableview with a list of appointments only occurring within a weeks time.
         * @param actionEvent Action Event for Sort by Week Radio Button*/
    public void onSortWeek(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
        LocalDateTime datePlusWeek = LocalDateTime.now().plusWeeks(1);

        for(Appointments a: appointments){
            if(a.getStartDateTime().isBefore(datePlusWeek) || a.getStartDateTime().isEqual(datePlusWeek)){
                filteredAppointments.add(a);

            }
        }
        refreshAppointments(filteredAppointments);
    }

    /**Method removes any filters from the appointments table view
     * @param actionEvent Action Event for No Filter Radio Button*/
    public void onNoFilter(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        for(Appointments A : appointments){
            refreshAppointments(appointments);
        }
    }

    /**Initialize method populates tableview with a list of all appointments.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        for(Appointments A : appointments){
            refreshAppointments(appointments);
        }
    }
}
