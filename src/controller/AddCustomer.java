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

    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);
    }

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

    public void onStateProvince(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> firstLevelDivisions = DatabaseLocations.getSelectedFirstLevelDivisions(
                addCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryID());

        stateProvinceComboBox.setItems(firstLevelDivisions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countries = DatabaseLocations.getAllCountries();
        addCustomerCountryComboBox.setItems(countries);
        }
    }


