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

/**Controller Class for UpdateCustomer*/
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

    private static Customers customerToModify = null;


    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**LAMDA 1 IS USED HERE MOST METHODS USED FOR NAVIGATION WILL USE SAID LAMBDA. JUSTIFICATION: CLEANS UP CODE,
     * SIGNIFICANTLY REDUCING CODE BLOAT FROM 6 LINES OF CODE DOWN TO 1 PER METHOD USED FOR NAVIGATION.
     * Method used when cancel button is pressed.
     * @param actionEvent Action Event for Cancel Button*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);
    }

    /**Gathers all information entered in and sends it to DatabaseCustomers.updateCustomer. LAMBDA 1 is also used here.
     * @param actionEvent Action Event for Update Button*/
    public void onUpdate(ActionEvent actionEvent) throws IOException {
        int customerId = Integer.parseInt(updateCustomerId.getText());
        String customerName = updateCustomerName.getText();
        String customerPhone = updateCustomerPhone.getText();
        String customerAddress = updateCustomerAddress.getText();
        String customerZip = updateCustomerZip.getText();
        int customerDivisionID = stateProvince.getSelectionModel().getSelectedItem().getDivisionID();

        DatabaseCustomers.updateCustomer(customerId, customerName, customerAddress, customerZip, customerPhone, customerDivisionID);
        ErrorHandling.displayInformation("Customer successfully updated!");
        navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);

    }

    /**Populates States/Province combo box when user selects a country
     * @param actionEvent Action Event for States/Province combo box*/
    public void onCustomerCountry(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> firstLevelDivisions = DatabaseLocations.getSelectedFirstLevelDivisions
                (updateCustomerCountry.getSelectionModel().getSelectedItem().getCountryID());

        stateProvince.setItems(firstLevelDivisions);
    }

    /**Initialize method for Update Customers. Populates Country Combo Box with an Observable List of Countries.*/
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
