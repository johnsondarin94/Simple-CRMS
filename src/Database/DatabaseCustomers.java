package Database;


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

    public static void addCustomer(String customerName, String customerAddress, String customerZipCode, String customerPhone, String createdBy, String lastUpdatedBy) {
        try {

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, 6);
            ps.setString(2, customerName);
            ps.setString(3, customerAddress);
            ps.setString(4, customerZipCode);
            ps.setString(5, customerPhone);
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setString(7, createdBy);
            ps.setDate(8, Date.valueOf(LocalDate.now()));
            ps.setString(9, lastUpdatedBy);
            ps.setInt(10, 5);


            ps.executeUpdate();

            System.out.println("Added Customer to Database");

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(Integer customerId, String customerName, String customerAddress, String customerZipCode, String customerPhone){
        Date updateTime = Date.valueOf(LocalDate.now());
        try {

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE customers SET Customer_Name='"+customerName+"', " +
                    "Address='"+customerAddress+"', Postal_Code='"+customerZipCode+"', " + "Phone='"+customerPhone+"', Last_Update='"+updateTime+"' , " +
                    "Last_Updated_By='test' WHERE Customer_ID='"+customerId+"'");

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


