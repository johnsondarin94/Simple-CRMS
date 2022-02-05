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

/**Controller Class for Add Appointment page.*/
public class AddAppointment implements Initializable{
    public Button addButton;
    public Button cancelButton;
    public DatePicker appointmentStartDate;
    public TextField appointmentId;
    public TextField appointmentTitle;
    public TextArea appointmentDescription;
    public TextField appointmentType;
    public ComboBox<LocalTime> appointmentStartTime;
    public ComboBox<LocalTime>appointmentEndTime;
    public ComboBox userIDComboBox;
    public ComboBox customerIDComboBox;
    public ComboBox<Contacts> contactComboBox;
    public TextField location;

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**Method handles what happens when user pressed add. Grabs all entered values and makes several method calls to
     * check for overlap, check business hours as well as chronological ordering. If all return true, method calls the
     * appropriate database class to add appointment.
     * @param actionEvent Action Event for Add Button*/
    public void onAdd(ActionEvent actionEvent) throws IOException {
        try {
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


            if (checkForAptOverlap(customerId, startDateTime, endDateTime) && checkBusinessHours(startDateTime, endDateTime) && checkInverseHours(startDateTime, endDateTime)
                    && checkPopulatedFields(title, description, type, loca)) {

                DatabaseAppointments.addAppointment(title, description, loca, type, startDateTime, endDateTime, activeUser, customerId, userID, contactID);
                ErrorHandling.displayInformation("Successfully created appointment.\n" + title);
                navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1100, 550);
            }
        }
        catch (NullPointerException e){
            ErrorHandling.displayError("Please ensure all fields are populated.");
        }
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
            LocalDateTime start = oLap.getStartDateTime();
            LocalDateTime end = oLap.getEndDateTime();

            if(overlapList.isEmpty()){
                System.out.println("Statement 0 was reached");
                return true;
            }

            if ((sdt.isAfter(start) || sdt.isEqual(start)) && sdt.isBefore(end)) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                System.out.println("Statement 1 was reached");
                return false;
            }
            if (edt.isAfter(start) && (edt.isBefore(end) || edt.isEqual(end))) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                System.out.println("Statement 2 was reached");
                return false;
            }
            if ((sdt.isBefore(start) && (edt.isAfter(end)))) {
                ErrorHandling.displayError("There is an overlap of appointments with this Customer.");
                System.out.println("Statement 3 was reached");
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

    public boolean checkPopulatedFields(String title, String description, String type, String location){
        if(title.isEmpty() || description.isEmpty() || type.isEmpty()|| location.isEmpty()){
            ErrorHandling.displayError("Please ensure all text fields are populated.");
            return false;
        }
        return true;
    }

    /**Method returns user to Appointments page. LAMBDA 1 IS USED HERE
     * @param actionEvent Action Event for Cancel button*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1100, 550);
    }

    /**Initialize method populates Customer, Contacts, Times, and Users ComboBoxes*/
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
