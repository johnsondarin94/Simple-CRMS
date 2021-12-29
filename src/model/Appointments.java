package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Appointments {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Date startDateTime;
    private Date endDateTime;
    private int customerId;
    private int userId;

    public Appointments (int appointment_ID, String title, String description, String location, String contact, String type, Date startDateTime, Date endDateTime
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

    public int getAppointment_ID() {
        return appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
