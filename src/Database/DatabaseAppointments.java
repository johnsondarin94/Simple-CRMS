package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
