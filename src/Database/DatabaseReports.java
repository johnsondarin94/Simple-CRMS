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

/**DatabaseReports Class retrieves data from database to generate reports.*/
public class DatabaseReports {

    /**Queries a list of all types, counts total types and groups appointments to their respective type.
     * @return ObservableList of strings each containing the type.*/
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

    /**Queries a list of all months where there are upcoming appointments. Adds total number of appointments for each
     * respective month.
     * @return ObservableList Strings each containing a month and total appointments for that month.*/
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

    /**Queries a contacts upcoming appointments and adds them to a list. Passes in a contact ID and queries appointments where contactID matches appointment.
     * @param contactID Desired contactID (int) passed in to retrieve respective appointments.
     * @return ObservableList of Appointments objects. */
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
