package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DatabaseAppointments {
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, " +
                    "contacts.Contact_ID, contacts.Contact_Name, contacts.Email, appointments.Type, appointments.Start, appointments.End, " +
                    "appointments.Customer_ID, appointments.User_ID FROM appointments INNER JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID ORDER BY appointments.Contact_ID";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Contacts c = new Contacts(contactId, contactName, contactEmail);

                LocalDateTime sdt = startDateTime.toLocalDateTime();
                LocalDateTime edt = endDateTime.toLocalDateTime();

                Appointments a = new Appointments(appointmentId, title, description, location, c, type, sdt, edt, customerId, userId);

                appointmentsList.add(a);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    public static ObservableList<Appointments> getAssociatedAppointments(int id){

        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, " +
                    "contacts.Contact_ID, contacts.Contact_Name, contacts.Email, appointments.Type, appointments.Start, appointments.End, " +
                    "appointments.Customer_ID, appointments.User_ID FROM appointments INNER JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID " +
                    "INNER JOIN customers ON customers.Customer_ID = appointments.Customer_ID WHERE customers.Customer_ID = '"+id+"'";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Contacts c = new Contacts(contactId, contactName, contactEmail);

                LocalDateTime sdt = startDateTime.toLocalDateTime();
                LocalDateTime edt = endDateTime.toLocalDateTime();

                Appointments a = new Appointments(appointmentId, title, description, location, c, type, sdt, edt, customerId, userId);

                appointmentsList.add(a);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String createdBy, int customerId, int userId, int contactID){
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String s = dtf.format(startDateTime);
            String e = dtf.format(endDateTime);
            startDateTime = startDateTime.parse(s, dtf);
            endDateTime = endDateTime.parse(e, dtf);

            Timestamp st = Timestamp.valueOf(startDateTime);
            Timestamp et = Timestamp.valueOf(endDateTime);
            System.out.println(st);

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, st);
            ps.setTimestamp(6, et);
            ps.setDate(7, Date.valueOf(LocalDate.now()));
            ps.setString(8,  createdBy);
            ps.setDate(9, Date.valueOf(LocalDate.now()));
            ps.setString(10, createdBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactID);

            ps.executeUpdate();
            System.out.println("Successfully added appointment");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String activeUser, int customerId, int userId, int contactId){
        Date updateTime = Date.valueOf(LocalDate.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = dtf.format(startDateTime);
        String e = dtf.format(endDateTime);
        startDateTime = startDateTime.parse(s, dtf);
        endDateTime = endDateTime.parse(e, dtf);

        Timestamp st = Timestamp.valueOf(startDateTime);
        Timestamp et = Timestamp.valueOf(endDateTime);

        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE appointments SET Title='"+title+"', Description='"+description+"'," +
                    "Location='"+location+"', Type='"+type+"', Start='"+st+"', End='"+et+"', Last_Update='"+updateTime+"', Last_Updated_By='"+activeUser+"', " +
                    "Customer_ID='"+customerId+"', User_ID='"+userId+"', Contact_ID='"+contactId+"' WHERE Appointment_ID='"+id+"'");

            ps.executeUpdate();
            System.out.println("Successfully updated appointment");

        } catch (Exception g) {
            g.printStackTrace();
        }

    }

    public static void deleteAppointment(int appointmentId){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID='"+appointmentId+"'");

            ps.executeUpdate();

            System.out.println("Successfully deleted Appointment from database.");

        } catch(SQLException throwables){

        }
    }

    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM contacts");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts contacts = new Contacts(contactID, contactName, contactEmail);

                contactsList.add(contacts);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactsList;
    }

}
