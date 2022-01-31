package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**Appointments Class provides a model for an appointment object*/
public class Appointments {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private Contacts contact;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;

    /**Constructor for an Appointments object
     *
     * @param appointment_ID Appointment ID (int) for Appointments object.
     * @param title Title (String) for Appointments object.
     * @param description Description (String) for Appointments object.
     * @param location Location (String)for Appointments object.
     * @param contact Contact (object) for Appointments object.
     * @param type Type (String) for Appointments object.
     * @param startDateTime StartDateTime (LocalDateTime) for Appointments object.
     * @param endDateTime EndDateTime (LocalDateTime) for Appointments object.
     * @param customerId Customer ID (int) for Appointments object.
     * @param userId User ID (int) for Appointments object.
     *
     * */
    public Appointments (int appointment_ID, String title, String description, String location, Contacts contact, String type, LocalDateTime startDateTime, LocalDateTime endDateTime
    , int customerId, int userId){
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;

    }

    /**Getter for Appointment ID
     * @return Returns Appointment ID*/
    public int getAppointment_ID() {
        return appointment_ID;
    }

    /**Setter for Appointment ID
     * @param appointment_ID Sets Appointment ID*/
    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    /**Getter for Title
     * @return Return Title*/
    public String getTitle() {
        return title;
    }

    /**Setter for Title
     * @param title Sets Title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**Getter for Description
     * @return Return Description*/
    public String getDescription() {
        return description;
    }

    /**Setter for Description
     * @param description Sets Description*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**Getter for Location
     * @return Return Location*/
    public String getLocation() {
        return location;
    }

    /**Setter for Location
     * @param location Sets Location*/
    public void setLocation(String location) {
        this.location = location;
    }

    /**Getter for Contact
     * @return Return Contact*/
    public Contacts getContact() {
        return contact;
    }

    /**Setter for Contact
     * @param contact Sets Contact*/
    public void setContact(Contacts contact) {
        this.contact = contact;
    }

    /**Getter for Type
     * @return Return Type*/
    public String getType() {
        return type;
    }

    /**Setter for Type
     * @param type Sets Type*/
    public void setType(String type) {
        this.type = type;
    }

    /**Getter for StartDateTime
     * @return Return StartDateTime*/
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**Setter for StartDateTime
     * @param startDateTime Sets StartDateTime*/
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**Getter for EndDateTime
     * @return Return EndDateTime*/
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**Setter for EndDateTime
     * @param endDateTime Sets EndDateTime*/
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**Getter for CustomerID
     * @return Return CustomerID*/
    public int getCustomerId() {
        return customerId;
    }

    /**Setter for CustomerID
     *@param customerId Sets CustomerID*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**Getter for UserID
     * @return Return UserID*/
    public int getUserId() {
        return userId;
    }

    /**Setter for UserID
     * @param userId Sets UserID*/
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Generates hourly options for a combo box when choosing Appointment Time.
     * @return Return ObservableList of LocalTime objects
     * */
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

    /**Overrides the string class to properly display Appointment objects in the UI.
     * @return Returns overridden String*/
    @Override
    public String toString() {
        return (appointment_ID + " " + title + " " + type + " " + description + " " + startDateTime + " " + endDateTime + " " + customerId);
    }
}
