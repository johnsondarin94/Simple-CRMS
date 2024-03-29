package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**Handles all retrieval, adding, updating, and deleting all all Appointments*/
public class DatabaseAppointments {

    /**Queries a list of all appointments and returns an observable list of Appointments objects
     * @return Return an observable list of Appointments*/
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

    /**Queries a list of all appointments associated with a given integer ID
     * @param id ID(int) passed to retrieve appointments associated with it.
     * @return Returns an observable list of Appointments*/
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

    /**Inserts an Appointment into the database passing in necessary column data. Appointment ID is generated by database.
     * Prints stack trace if character limit is exceeded.
     * @param title Title(String) of appointment
     * @param description Description(String) of appointment
     * @param location Location(String) of appointment
     * @param type Type(String) of appointment
     * @param startDateTime Start Date Time(LocalDateTime) of appointment. Converted to UTC by database.
     * @param endDateTime End Date Time(LocalDateTime) of appointment. Converted to UTC by database.
     * @param createdBy Created By (String) Given by name of active Users object
     * @param customerId CustomerID(int) of appointment. Provided by getting Customer ID from selected Customers Object
     * @param userId UserID(int) of appointment. Provided by getting User ID from selected Users object
     * @param contactID ContactID(int) of appointment. Provided by getting contact ID from selected Contacts object*/
    public static boolean addAppointment(String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String createdBy, int customerId, int userId, int contactID) throws SQLException {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String s = dtf.format(startDateTime);
            String e = dtf.format(endDateTime);
            startDateTime = startDateTime.parse(s, dtf);
            endDateTime = endDateTime.parse(e, dtf);

            Timestamp st = Timestamp.valueOf(startDateTime);
            Timestamp et = Timestamp.valueOf(endDateTime);

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
        try{
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**Updates an appointment in the database replacing the original. Provides necessary column data through parameters.
     * Prints stack trace if character limit is exceeded
     * @param id ID(int) used to identify and replace original appointment in database.
     * @param title Title(String) of appointment
     * @param description Description(String) of appointment
     * @param location Location(String) of an appointment
     * @param type Type(String) of appointment
     * @param startDateTime Start Date Time (LocalDateTime) of appointment. Automatically converted to UTC by database
     * @param endDateTime End Date Time (LocalDateTime) of appointment. Automatically converted to UTC by database
     * @param activeUser Active User(String). Accesses String through the active Users object created at successful login
     * @param customerId CustomerID(int) of appointment. Provided by getting Customer ID from selected Customers Object
     * @param userId UserID(int) of appointment. Provided by getting User ID from selected Users object
     * @param contactId ContactID(int) of appointment. Provided by getting contact ID from selected Contacts object*/
    public static boolean updateAppointment(int id, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            String activeUser, int customerId, int userId, int contactId) throws SQLException {
        Date updateTime = Date.valueOf(LocalDate.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = dtf.format(startDateTime);
        String e = dtf.format(endDateTime);
        startDateTime = startDateTime.parse(s, dtf);
        endDateTime = endDateTime.parse(e, dtf);

        Timestamp st = Timestamp.valueOf(startDateTime);
        Timestamp et = Timestamp.valueOf(endDateTime);


           PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE appointments SET Title=?, " +
                   "Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, " +
                   "Contact_ID=? WHERE Appointment_ID=?");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, st);
            ps.setTimestamp(6, et);
            ps.setDate(7, updateTime);
            ps.setString(8, activeUser);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, id);

        try{
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (Exception g) {
            g.printStackTrace();
            return false;
        }

    }

    /**Removes an appointment using provided Appointment ID.
     * @param appointmentId Appointment ID(int) used to find and remove desired Appointment*/
    public static void deleteAppointment(int appointmentId){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID='"+appointmentId+"'");

            ps.executeUpdate();

        } catch(SQLException throwables){

        }
    }

    public static void deleteAssociatedAppointments(int customerID){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Customer_ID='"+customerID+"'");

            ps.executeUpdate();

        } catch(SQLException throwables){

        }
    }

    /**Queries and returns an observable list of Contacts objects
     * @return Returns an observablelist of Contacts objects*/
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
