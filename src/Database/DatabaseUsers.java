package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUsers {
    public static ObservableList<Users> getUsers(){
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString("Password");

                Users u = new Users(userId, userName, passWord, false);

                userList.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
 }
