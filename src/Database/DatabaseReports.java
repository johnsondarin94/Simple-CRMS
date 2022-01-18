package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
/*
    public static ObservableList<String> getContactSchedule(){
        ObservableList<String> contactSchedule = FXCollections.observableArrayList();

        try{
            String sql = ""
        }
    }
*/
}
