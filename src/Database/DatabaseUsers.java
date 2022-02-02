package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**DatabaseUsers Class accesses Users table. Provides 2 methods to access and retrieve Users.*/
public class DatabaseUsers {

    /**getUsers returns an observable list of all users in database.
     * @return ObservableList of Users objects*/
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

    /**getSpecifiedUser returns a Users object with the provided userID
     * @param userID UserID(int) of desired Users object.
     * @return Returns Users object using provided UserID*/
    public static Users getSpecificUser(int userID){
        Users user = null;
        try{
            String sql = "SELECT * FROM users WHERE users.User_ID = '"+userID+"'";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString("Password");

                Users u = new Users(userId, userName, passWord, false);

                user = u;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
 }
