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

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onAdd(ActionEvent actionEvent) {
        String activeUser = Login.getUserHandoff().getUserName();
        String customerName = addCustomerName.getText();
        String address = addCustomerAddress.getText();
        String zipCode = addCustomerZip.getText();
        String phone = addCustomerPhone.getText();
        int divisionID =  stateProvinceComboBox.getSelectionModel().getSelectedItem().getDivisionID();

        String countryName = addCustomerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName();
        String stateProvinceName = stateProvinceComboBox.getSelectionModel().getSelectedItem().getDivisionName();

        DatabaseCustomers.addCustomer(customerName, address, zipCode, phone, activeUser, activeUser, divisionID);
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


