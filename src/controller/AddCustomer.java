package controller;

import Database.DatabaseCustomers;
import Database.DatabaseLocations;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;
import model.Users;
import util.ErrorHandling;
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Controller Class for Add Customer Page*/
public class AddCustomer implements Initializable {
    public Button cancelButton;
    public TextField addCustomerPhone;
    public TextField addCustomerName;
    public TextField addCustomerAddress;
    public TextField addCustomerZip;
    public TextField addCustomerId;
    public Button addButton;
    public ComboBox<Countries> addCustomerCountryComboBox;
    public ComboBox<FirstLevelDivisions> stateProvinceComboBox;

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**Method Navigates user to Appointments page. Any changes to Add Customer form are forgotten. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for Cancel button*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);
    }

    /**Method handles adding a customer. Takes in all information provided and sends it to the DatabaseCustomer Class
     * to get added to the database. Must populate all fields or it will display error.
     * @param actionEvent Action Event for Add Button.*/
    public void onAdd(ActionEvent actionEvent) throws IOException {
        try{
            String activeUser = Login.getUserHandoff().getUserName();
            String customerName = addCustomerName.getText();
            String address = addCustomerAddress.getText();
            String zipCode = addCustomerZip.getText();
            String phone = addCustomerPhone.getText();
            int divisionID =  stateProvinceComboBox.getSelectionModel().getSelectedItem().getDivisionID();

            DatabaseCustomers.addCustomer(customerName, address, zipCode, phone, activeUser, activeUser, divisionID);
            ErrorHandling.displayInformation("Customer Successfully added!");
            navigate.navigate(actionEvent,"/view/Customers.fxml" ,"Customers", 1100, 550);

        } catch (NullPointerException e) {
            ErrorHandling.displayError("Please ensure all fields are populated.");
        }
    }

    /**Method populates States/Province combo box as soon as user selects a country.
     * @param actionEvent Action Event for StatesProvince combo box*/
    public void onStateProvince(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> firstLevelDivisions = DatabaseLocations.getSelectedFirstLevelDivisions(
                addCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryID());

        stateProvinceComboBox.setItems(firstLevelDivisions);
    }

    /**Initialize method for the add Customer page. Populates Countries combo box with a list of all Countries provided
     * by database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countries = DatabaseLocations.getAllCountries();
        addCustomerCountryComboBox.setItems(countries);
        }
    }


