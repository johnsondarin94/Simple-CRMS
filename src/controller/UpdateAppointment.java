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
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import util.ErrorHandling;
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/**Controller Class for UpdateAppointments*/
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

    /**LAMDA 1 IS USED HERE MOST METHODS USED FOR NAVIGATION WILL USE SAID LAMBDA. JUSTIFICATION: CLEANS UP CODE,
     * SIGNIFICANTLY REDUCING CODE BLOAT FROM 6 LINES OF CODE DOWN TO 1 PER METHOD USED FOR NAVIGATION.
     * Method used when cancel button is pressed.
     * @param actionEvent Action Event for Cancel Button*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1100, 550);
    }

    /**Gathers all information entered and passes it to DatabaseAppointments.updateAppointment. LAMBDA 1 IS ALSO USED HERE
     * @param actionEvent Action Event for Update Button*/
    public void onUpdate(ActionEvent actionEvent) {
        try {
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
            LocalTime endTime = appointmentEndTime.getSelectionModel().getSelectedItem();

            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

            if (checkForAptOverlap(customerId, startDateTime, endDateTime) && checkBusinessHours(startDateTime, endDateTime) && checkInverseHours(startDateTime, endDateTime)
                    && checkPopulatedFields(title, description, type, location)) {

                if(DatabaseAppointments.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID)) {
                    ErrorHandling.displayInformation("Appointment successfully updated");
                    navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1000, 550);
                }
                else{
                    ErrorHandling.displayError("Please ensure fields do not exceed character limit (50).");
                }
            }
        }
        catch (Exception e){
            ErrorHandling.displayError("Please ensure all fields are populated");
        }
    }

    public boolean checkPopulatedFields(String title, String description, String type, String location){
        if(title.isEmpty() || description.isEmpty() || type.isEmpty()|| location.isEmpty()){
            ErrorHandling.displayError("Please ensure all text fields are populated.");
            return false;
        }
        return true;
    }

    /**Method checks to make sure end time does not fall before start time. Returns a boolean based on result.
     * @param sdt sdt(LocalDateTime) entered by user
     * @param edt edt(LocalDateTime) entered by user
     * @return Returns boolean based on result*/
    public boolean checkInverseHours(LocalDateTime sdt, LocalDateTime edt){
        if(sdt.isAfter(edt) || edt.isBefore(sdt) || sdt.isEqual(edt)){
            ErrorHandling.displayError("Please check start and date times for chronological order.");
            return false;
        }
        return true;
    }

    /**Method checks for appointment overlap with other appointments for a given customer.
     * @param customerId Customer ID is used to grab associated appointments with a given customer
     * @param sdt sdt(LocalDateTime) Start Date and Time for desired appointment
     * @param edt edt(LocalDateTime) End Date and Time for desired appointment
     * @return Returns boolean based on result*/
    public boolean checkForAptOverlap(int customerId, LocalDateTime sdt, LocalDateTime edt) {
        ObservableList<Appointments> overlapList = DatabaseAppointments.getAssociatedAppointments(customerId);

        for (Appointments oLap : overlapList) {
            if(oLap.getAppointment_ID() == appointmentToModify.getAppointment_ID()){
                continue;
            }
            LocalDateTime start = oLap.getStartDateTime();
            LocalDateTime end = oLap.getEndDateTime();
            if(overlapList.isEmpty()){
                System.out.println("Statement 0 was reached");
                return true;
            }

            if ((sdt.isAfter(start) || sdt.isEqual(start)) && sdt.isBefore(end)) {
                System.out.println("Statement 1 was reached");
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                return false;
            }
            if (edt.isAfter(start) && (edt.isBefore(end) || edt.isEqual(end))) {
                System.out.println("Statement 2 was reached");
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                return false;
            }

            if ((sdt.isBefore(start) && (edt.isAfter(end)))) {
                System.out.println("Statement 3 was reached");
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                return false;
            }

             else {

                System.out.println("Statement 4 was reached");
                return true;
            }
        }
        return true;
    }

    /**Method checks desired appointment date and time with the businesses hours which are stored in EST to ensure
     * business will be open at time of appointment.
     * @param sdt sdt(LocalDateTime) entered by user used to compare with business hours
     * @param edt edt(LocalDateTime) entered by user used to compare with business hours
     * @return Returns a boolean based on result*/
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
            ErrorHandling.displayError("Appointment does not fall withing business hours. 8am - 10pm EST");
            return false;
        }
        else{
            return true;
        }
    }

    /**Initialize method for UpdateAppointment populates all fields on the form with data grabbed from Appointments Controller*/
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
}
