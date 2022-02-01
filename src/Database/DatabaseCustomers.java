package Database;


import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**DatabaseCustomers handles all queries related to Customers objects. Handles retrieving, inserting, updating and deleting
 * of all customers objects.*/
public class DatabaseCustomers {

    /**Queries a list of all Customers, creates the Customers objects and adds them to an observable list
     * @return Observable List of Customers objects*/
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        try {
            String sql = "select customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                    "customers.Phone, countries.Country, countries.Country_ID, first_level_divisions.Division_ID, first_level_divisions.Division FROM customers INNER JOIN first_level_divisions " +
                    "ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerZipCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Countries countries = new Countries(countryId, country);
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division);

                Customers c = new Customers(customerId, customerName, customerAddress, customerZipCode, customerPhone,
                        countries, fld);

                customerList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return customerList;
    }

    /**Queries a specified Customer based on a customer ID and creates a Customers object.
     * @param customerID ID(int) used to retrieve desired customer
     * @return Customers object*/
    public static Customers getUpdateCustomer(int customerID){
        Customers customers = null;
        try{
            String sql = "select customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, \n" +
                    "\tcustomers.Phone, countries.Country, countries.Country_ID, first_level_divisions.Division_ID, first_level_divisions.Division FROM customers INNER JOIN first_level_divisions\n" +
                    "\tON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE customers.Customer_ID ='"+customerID+"'";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerZipCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Countries countries = new Countries(countryId, country);
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division);

                Customers c = new Customers(customerId, customerName, customerAddress, customerZipCode, customerPhone,
                        countries, fld);
                customers = c;
                
            }
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    /**Inserts a customer into the database. Takes in customers necessary column data and inserts into database.
     * @param customerName Customers Name(String)
     * @param customerAddress Customers Address (String)
     * @param customerZipCode Customers Zip Code(String)
     * @param customerPhone  Customers Phone Number (String)
     * @param createdBy Created By(String) retrieved from active user.
     * @param lastUpdatedBy Last Updated By (String) retrieved from active user
     * @param divisionID Division ID (int) retrieved from first level divisions combo box*/
    public static void addCustomer(String customerName, String customerAddress, String customerZipCode, String customerPhone, String createdBy, String lastUpdatedBy, int divisionID) {
        try {
            LocalDate nowDate = LocalDate.now();
            LocalTime nowTime = LocalTime.now();
            LocalDateTime nowDateTime = LocalDateTime.of(nowDate, nowTime);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO customers SET Customer_Name='"+customerName+"', " +
                    "Address='"+customerAddress+"', Postal_Code='"+customerZipCode+"', " + "Phone='"+customerPhone+"', "+ "Create_Date='"+nowDateTime+"', "+
                    "Created_By='"+createdBy+"',"+"Last_Updated_By='"+lastUpdatedBy+"', "+"Division_ID='"+divisionID+"'");

            ps.executeUpdate();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**Updates a customer in the database. Takes in necessary data and replaces a customer in database with matching ID
     * @param customerId Customer ID (int) used to locate and replace desired customer
     * @param customerName Customer Name (String)
     * @param customerAddress Customers Address (String)
     * @param customerZipCode Customers ZipCode (String)
     * @param customerPhone Customers Phone Number (String)
     * @param customerDivisionID Customers State or Province ID (int)*/
    public static void updateCustomer(int customerId, String customerName, String customerAddress, String customerZipCode, String customerPhone, int customerDivisionID){
        LocalDateTime updateTime = LocalDateTime.now();
        String lastUpdatedBy = Login.getUserHandoff().getUserName();
        try {

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE customers SET Customer_Name='"+customerName+"', " +
                    "Address='"+customerAddress+"', Postal_Code='"+customerZipCode+"', " + "Phone='"+customerPhone+"', Last_Update='"+updateTime+"' , " +
                    "Last_Updated_By='"+lastUpdatedBy+"' , " + "Division_ID='"+customerDivisionID+"' WHERE Customer_ID='"+customerId+"'");

            ps.executeUpdate();


        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**Removes desired customer from database. Takes in a customer ID to locate intended customer.
     * @param customerId Customer ID (int) used to find customer*/
    public static void deleteCustomer(int customerId){
        try{

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Customers WHERE Customer_ID='"+customerId+"'");

            ps.executeUpdate();

        } catch(SQLException throwables){

        }
    }
    }


