package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseAppointments {
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_ID");
                String type = rs.getString("Type");
                Date startDateTime = rs.getDate("Start");
                Date endDateTime = rs.getDate("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointments a = new Appointments(appointmentId, title, description, location, contact, type, startDateTime, endDateTime, customerId, userId);

                appointmentsList.add(a);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    public static ObservableList<Integer> getAssociatedAppointments(int id){

        ObservableList<Integer> appointmentsList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT appointments.Customer_ID FROM appointments INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
              /*  String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_ID");
                String type = rs.getString("Type");
                Date startDateTime = rs.getDate("Start");
                Date endDateTime = rs.getDate("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
*/
                //Appointments a = new Appointments(appointmentId, title, description, location, contact, type, startDateTime, endDateTime, customerId, userId);

                appointmentsList.add(customerId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    public static void addAppointment(String title, String description, String contact, String type, Date startDateTime, Date endDateTime, int customerId, int userId){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, contact);
            ps.setString(4, type);
            ps.setDate(5, Date.valueOf(LocalDate.now())); //FIX ME POPULATE WITH LOCAL DATE AND TIME
            ps.setDate(6, Date.valueOf(LocalDate.now())); //FIX ME POPULATE WITH LOCAL DATE AND TIME
            ps.setDate(7, Date.valueOf(LocalDate.now())); //FIX ME POPULATE WITH LOCAL DATE AND TIME
            ps.setString(8, "test"); //FIX ME POPULATE WITH CURRENT USER NAME
            ps.setDate(9, Date.valueOf(LocalDate.now()));//FIX ME POPULATE WITH LAST UPDATE DATE AND TIME
            ps.setString(10, "test"); //FIX ME POPULATE WITH USER NAME
            ps.setInt(11, customerId);
            ps.setInt(12, userId); // FIX ME POPULATE WITH CURRENT USER ID
            ps.setInt(13, 1); // FIX ME POPULATE WITH CONTACT ID

            ps.executeUpdate();
            System.out.println("Successfully added appointment");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void updateAppointment(int id, String title, String description, String contact, String type){
        Date updateTime = Date.valueOf(LocalDate.now());
        Date startDateTime = Date.valueOf(LocalDate.now()); //FIXED TO PULL FROM COMBO BOX IN UI
        Date endDateTime = Date.valueOf(LocalDate.now()); //FIXED TO PULL FROM COMBO BOX IN UI
        String userName = "test";

        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE appointments SET Title='"+title+"', Description='"+description+"'," +
                    "Location='"+contact+"', Type='"+type+"', Start='"+startDateTime+"', End='"+endDateTime+"', Last_Update='"+updateTime+"', Last_Updated_By='"+userName+"' WHERE Appointment_ID='"+id+"'");

            ps.executeUpdate();
            System.out.println("Successfully updated appointment");

        } catch (Exception e) {
            e.printStackTrace();
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
}
