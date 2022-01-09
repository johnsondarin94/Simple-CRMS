package Database;


import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseCustomers {
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerZipCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdate = rs.getDate("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                Customers c = new Customers(customerId, customerName, customerAddress, customerZipCode, customerPhone,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);

                customerList.add(c);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return customerList;
    }

    public static void addCustomer(String customerName, String customerAddress, String customerZipCode, String customerPhone, String createdBy, String lastUpdatedBy, int divisionID) {
        try {

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerZipCode);
            ps.setString(4, customerPhone);
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setString(6, createdBy);
            ps.setDate(7, Date.valueOf(LocalDate.now()));
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionID);

            ps.executeUpdate();

            System.out.println("Added Customer to Database");

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(int customerId, String customerName, String customerAddress, String customerZipCode, String customerPhone){
        Date updateTime = Date.valueOf(LocalDate.now());
        String lastUpdatedBy = Login.getUserHandoff().getUserName();
        try {

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE customers SET Customer_Name='"+customerName+"', " +
                    "Address='"+customerAddress+"', Postal_Code='"+customerZipCode+"', " + "Phone='"+customerPhone+"', Last_Update='"+updateTime+"' , " +
                    "Last_Updated_By='"+lastUpdatedBy+"' WHERE Customer_ID='"+customerId+"'");

            ps.executeUpdate();

            System.out.println("Updated Customer to Database");

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
    public static void deleteCustomer(int customerId){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Customers WHERE Customer_ID='"+customerId+"'");

            ps.executeUpdate();

            System.out.println("Successfully deleted Customer from database.");

        } catch(SQLException throwables){

        }
    }
    }


