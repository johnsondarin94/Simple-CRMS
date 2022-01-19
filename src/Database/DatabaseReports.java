package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseReports {
    public static ObservableList<String> getReportByType(){
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Type, COUNT(Type) FROM appointments GROUP BY Type";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String type = rs.getString("Type");
                int total = rs.getInt("COUNT(Type)");

                String typeTotal = (type + ": " + total);
                typeList.add(typeTotal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public static ObservableList<String> getReportByMonth(){
        ObservableList<String> monthList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT MONTHNAME(Start), COUNT(MONTH(Start))from appointments GROUP BY MONTHNAME(Start)";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String month = rs.getString("MONTHNAME(Start)");
                int total = rs.getInt("COUNT(MONTH(Start))");

                String monthTotal = (month + ": " + total);
                monthList.add(monthTotal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return monthList;
    }

    public static ObservableList<Contacts> getContacts(){
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts c = new Contacts(id, name, email);
                contactsList.add(c);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsList;
    }

    public static ObservableList<Appointments> getContactAppointments(int contactID){
        ObservableList<Appointments> contactsAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, " +
                    "contacts.Contact_ID, contacts.Contact_Name, contacts.Email, appointments.Type, appointments.Start, appointments.End, " +
                    "appointments.Customer_ID, appointments.User_ID FROM appointments INNER JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID " +
                    " WHERE appointments.Contact_ID = '"+contactID+"'";

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

                contactsAppointments.add(a);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsAppointments;
    }


}
