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
import model.Customers;
import model.FirstLevelDivisions;
import util.ErrorHandling;
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable{
    public TextField updateCustomerName;
    public TextField updateCustomerPhone;
    public TextField updateCustomerAddress;
    public TextField updateCustomerZip;
    public TextField updateCustomerId;
    public ComboBox<Countries> updateCustomerCountry;
    public Button cancelButton;
    public Button updateButton;
    public ComboBox<FirstLevelDivisions> stateProvince;

    private Customers customerToModify = null;

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

    public void onUpdate(ActionEvent actionEvent) throws IOException {
        int customerId = Integer.parseInt(updateCustomerId.getText());
        String customerName = updateCustomerName.getText();
        String customerPhone = updateCustomerPhone.getText();
        String customerAddress = updateCustomerAddress.getText();
        String customerZip = updateCustomerZip.getText();
        int customerDivisionID = stateProvince.getSelectionModel().getSelectedItem().getDivisionID();

        DatabaseCustomers.updateCustomer(customerId, customerName, customerPhone, customerAddress, customerZip, customerDivisionID);
        ErrorHandling.displayInformation("Customer successfully updated!");
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);

    }

    public void onCustomerCountry(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> firstLevelDivisions = DatabaseLocations.getSelectedFirstLevelDivisions
                (updateCustomerCountry.getSelectionModel().getSelectedItem().getCountryID());

        stateProvince.setItems(firstLevelDivisions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countries = DatabaseLocations.getAllCountries();
        updateCustomerCountry.setItems(countries);

        customerToModify = CustomerController.getCustomerHandOff();
        updateCustomerId.setText(String.valueOf(customerToModify.getCustomerId()));
        updateCustomerName.setText(String.valueOf(customerToModify.getName()));
        updateCustomerAddress.setText(String.valueOf(customerToModify.getAddress()));
        updateCustomerPhone.setText(String.valueOf(customerToModify.getPhoneNumber()));
        updateCustomerZip.setText(String.valueOf(customerToModify.getZipCode()));
        updateCustomerCountry.setValue(customerToModify.getCountry());
        stateProvince.setValue(customerToModify.getDivision());

    }
}
